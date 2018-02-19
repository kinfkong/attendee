/*
 * Copyright (c) 2018 TopCoder, Inc. All rights reserved.
 */
package com.wiproevents.services;

import com.wiproevents.entities.EventNotification;
import com.wiproevents.entities.criteria.EventNotificationSearchCriteria;

/**
 * The event notification service.Implementation should be effectively thread-safe.
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public interface EventNotificationService extends GenericService<EventNotification, EventNotificationSearchCriteria> {
}

