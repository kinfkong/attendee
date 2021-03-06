/*
 * Copyright (c) 2018 TopCoder, Inc. All rights reserved.
 */
package com.wiproevents.services;

import com.wiproevents.entities.Notification;
import com.wiproevents.entities.criteria.NotificationSearchCriteria;
import com.wiproevents.exceptions.AttendeeException;

/**
 * The notification service implementation should be effectively thread-safe.
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public interface NotificationService extends GenericService<Notification, NotificationSearchCriteria> {
    void markRead(String notificationId) throws AttendeeException;
}

