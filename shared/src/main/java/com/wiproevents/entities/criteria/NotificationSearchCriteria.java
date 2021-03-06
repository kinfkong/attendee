/*
 * Copyright (c) 2018 TopCoder, Inc. All rights reserved.
 */
package com.wiproevents.entities.criteria;

import com.wiproevents.entities.statuses.NotificationStatus;
import lombok.Getter;
import lombok.Setter;


/**
 * The notification search criteria.
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
@Getter
@Setter
public class NotificationSearchCriteria extends BaseSearchCriteria {
    private NotificationStatus status;
}

