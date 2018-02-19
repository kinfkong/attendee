/*
 * Copyright (c) 2018 TopCoder, Inc. All rights reserved.
 */
package com.wiproevents.services.springdata;

import com.wiproevents.entities.Email;
import com.wiproevents.entities.Timezone;
import com.wiproevents.entities.criteria.EmailSearchCriteria;
import com.wiproevents.entities.statuses.EmailStatus;
import com.wiproevents.exceptions.AttendeeException;
import com.wiproevents.services.EmailService;
import com.wiproevents.utils.Helper;
import com.wiproevents.utils.springdata.extensions.DocumentDbSpecification;
import com.wiproevents.utils.springdata.extensions.SearchResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.util.Date;

/**
 * The Spring Data JPA implementation of EmailService,
 * extends BaseService<Email, EmailSearchCriteria>. Effectively thread safe after configuration.
 */
@Service
public class EmailServiceImpl
        extends BaseService<Email, EmailSearchCriteria> implements EmailService {

    @Autowired
    private TimezoneRepository timezoneRepository;

    /**
     * The java mail sender.
     */
    @Autowired
    private JavaMailSender javaMailSender;

    /**
     * The default from email address.
     */
    @Value("${mail.from}")
    private String fromAddress;


    /**
     * This method is used to get the specification.
     *
     * @param criteria the search criteria
     * @return the specification
     * @throws AttendeeException if any other error occurred during operation
     */
    @Override
    protected DocumentDbSpecification<Email> getSpecification(EmailSearchCriteria criteria)
            throws AttendeeException {
        return new EmailSpecification(criteria);
    }


    @Override
    public Email create(Email entity) throws AttendeeException {
        // check emails field
        Helper.checkNull(entity.getEmails(), "emails");
        for (String email : entity.getEmails()) {
            Helper.checkEmail(email, "email");
        }
        if (!entity.isScheduled()) {
            // send immediately
            for (String email: entity.getEmails()) {
                sendEmail(email, entity.getTitle(), entity.getText());
            }
            entity.setStatus(EmailStatus.Sent);
        } else {
            entity.setStatus(EmailStatus.Scheduled);
            Helper.checkNull(entity.getDateScheduled(), "dateScheduled");

            // modify the time by timezone
            if (entity.getTimezone() != null && entity.getTimezone().getId() != null) {
                Timezone timezone = timezoneRepository.findOne(entity.getTimezone().getId());

                if (timezone == null) {
                    throw new IllegalArgumentException("The timezone id: "
                            + entity.getTimezone().getId() + " does not exist.");
                }

                String timezoneSuffix = timezone.getTime().replace("GMT", "");
                String dateStr = Helper.toISO8601UTC(entity.getDateScheduled());
                dateStr = dateStr.replace("Z", timezoneSuffix);
                entity.setDateScheduled(Helper.fromTimezoneDateString(dateStr));
            }
        }
        return super.create(entity);
    }

    /**
     * Send email with to email address, email fullName and model params.
     *
     * @param toEmail  the email to send
     * @param title the email subject
     * @param text the email body
     * @throws AttendeeException throws if error to send email.
     */
    private void sendEmail(String toEmail, String title, String text) throws AttendeeException {
        try {
            MimeMessage mail = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mail);
            helper.setTo(toEmail);
            helper.setFrom(fromAddress);
            helper.setSubject(title);
            helper.setText(text, true);
            javaMailSender.send(mail);
        } catch (MessagingException | MailException e) {
            throw new AttendeeException("Error to send email", e);
        }
    }

    public int sendScheduledEmails() throws AttendeeException {
        // fetch the new emails
        EmailSearchCriteria criteria = new EmailSearchCriteria();
        criteria.setStatus(EmailStatus.Scheduled);
        SearchResult<Email> emails = search(criteria, null);
        int counter = 0;
        for (Email entity: emails.getEntities()) {
            if (entity.getDateScheduled() == null
                    || entity.getDateScheduled().getTime() > new Date().getTime()) {
                continue;
            }
            for (String email: entity.getEmails()) {
                sendEmail(email, entity.getTitle(), entity.getText());
                // update the status
                entity.setStatus(EmailStatus.Sent);
                update(entity.getId(), entity);
                counter++;
            }
        }
        return counter;
    }
}

