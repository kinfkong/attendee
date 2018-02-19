/*
 * Copyright (c) 2018 TopCoder, Inc. All rights reserved.
 */
package com.wiproevents.services.springdata;

import com.wiproevents.entities.EventNotification;
import com.wiproevents.entities.criteria.EventNotificationSearchCriteria;
import com.wiproevents.exceptions.AttendeeException;
import com.wiproevents.services.EventNotificationService;
import com.wiproevents.utils.springdata.extensions.DocumentDbSpecification;
import org.springframework.stereotype.Service;

/**
 * The Spring Data JPA implementation of EventNotificationService,
 * extends BaseService<EventNotification, EventNotificationSearchCriteria>. Effectively thread safe after configuration.
 */
@Service
public class EventNotificationServiceImpl
        extends BaseService<EventNotification, EventNotificationSearchCriteria> implements EventNotificationService {

    /**
     * This method is used to get the specification.
     *
     * @param criteria the search criteria
     * @return the specification
     * @throws AttendeeException if any other error occurred during operation
     */
    @Override
    protected DocumentDbSpecification<EventNotification> getSpecification(EventNotificationSearchCriteria criteria)
            throws AttendeeException {
        return new EventNotificationSpecification(criteria);
    }
}

