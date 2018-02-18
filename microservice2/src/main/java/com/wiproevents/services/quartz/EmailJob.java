package com.wiproevents.services.quartz;

import com.wiproevents.exceptions.AttendeeException;
import com.wiproevents.services.EmailService;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by gkatzioura on 6/6/16.
 */
public class EmailJob implements Job {

    @Autowired
    private EmailService emailService;

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        // trigger the event
        try {
            int c = emailService.sendScheduledEmails();
            if (c > 0) {
                System.out.println("Sent " + c + " scheduled emails");
            }

        } catch (AttendeeException e) {
            // wrap and re-throw
            throw new JobExecutionException("error in executing the job.", e);
        }

    }
}