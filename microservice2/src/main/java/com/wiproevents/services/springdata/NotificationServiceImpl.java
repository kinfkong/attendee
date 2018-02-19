/*
 * Copyright (c) 2018 TopCoder, Inc. All rights reserved.
 */
package com.wiproevents.services.springdata;

import com.wiproevents.entities.Notification;
import com.wiproevents.entities.criteria.NotificationSearchCriteria;
import com.wiproevents.entities.statuses.NotificationStatus;
import com.wiproevents.exceptions.AttendeeException;
import com.wiproevents.exceptions.EntityNotFoundException;
import com.wiproevents.services.NotificationService;
import com.wiproevents.utils.Helper;
import com.wiproevents.utils.springdata.extensions.DocumentDbSpecification;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * The Spring Data JPA implementation of NotificationService,
 * extends BaseService<Notification, NotificationSearchCriteria>. Effectively thread safe after configuration.
 */
@Service
public class NotificationServiceImpl
        extends BaseService<Notification, NotificationSearchCriteria> implements NotificationService {

    @Override
    public void markRead(String notificationId) throws AttendeeException {
        Helper.checkNullOrEmpty(notificationId, "notificationId");
        Notification notification = getRepository().findOne(notificationId);
        if (notification == null) {
            throw new EntityNotFoundException("cannot find notification of id: " + notificationId);
        }
        notification.setReadOn(new Date());
        notification.setStatus(NotificationStatus.Read);
        update(notificationId, notification);
    }

    /**
     * This method is used to get the specification.
     *
     * @param criteria the search criteria
     * @return the specification
     * @throws AttendeeException if any other error occurred during operation
     */
    @Override
    protected DocumentDbSpecification<Notification> getSpecification(NotificationSearchCriteria criteria)
            throws AttendeeException {
        return new NotificationSpecification(criteria);
    }

    @Override
    protected void handleNestedCreate(Notification entity) throws AttendeeException {
        entity.setStatus(NotificationStatus.New);
        entity.setCreatedOn(new Date());
        entity.setReadOn(null);
        super.handleNestedCreate(entity);
    }
}

