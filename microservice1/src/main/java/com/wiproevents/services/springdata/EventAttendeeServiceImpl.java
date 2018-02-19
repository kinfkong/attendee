/*
 * Copyright (c) 2018 TopCoder, Inc. All rights reserved.
 */
package com.wiproevents.services.springdata;

import com.wiproevents.entities.EventAttendee;
import com.wiproevents.entities.criteria.EventAttendeeSearchCriteria;
import com.wiproevents.exceptions.AttendeeException;
import com.wiproevents.services.EventAttendeeService;
import com.wiproevents.utils.springdata.extensions.DocumentDbSpecification;
import org.springframework.stereotype.Service;

/**
 * The Spring Data JPA implementation of EventAttendeeService,
 * extends BaseService<EventAttendee, EventAttendeeSearchCriteria>. Effectively thread safe after configuration.
 */
@Service
public class EventAttendeeServiceImpl
        extends BaseService<EventAttendee, EventAttendeeSearchCriteria> implements EventAttendeeService {

    /**
     * This method is used to get the specification.
     *
     * @param criteria the search criteria
     * @return the specification
     * @throws AttendeeException if any other error occurred during operation
     */
    @Override
    protected DocumentDbSpecification<EventAttendee> getSpecification(EventAttendeeSearchCriteria criteria)
            throws AttendeeException {
        return new EventAttendeeSpecification(criteria);
    }
}

