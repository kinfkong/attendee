/*
 * Copyright (c) 2018 TopCoder, Inc. All rights reserved.
 */
package com.wiproevents.services;

import com.wiproevents.entities.Email;
import com.wiproevents.entities.criteria.EmailSearchCriteria;
import com.wiproevents.exceptions.AttendeeException;

/**
 * The email service implementation should be effectively thread-safe.
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public interface EmailService extends GenericService<Email, EmailSearchCriteria> {
    int sendScheduledEmails() throws AttendeeException;
}

