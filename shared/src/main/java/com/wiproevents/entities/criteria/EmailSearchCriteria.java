/*
 * Copyright (c) 2018 TopCoder, Inc. All rights reserved.
 */
package com.wiproevents.entities.criteria;

import com.wiproevents.entities.statuses.EmailStatus;
import lombok.Getter;
import lombok.Setter;


/**
 * The email search criteria.
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
@Getter
@Setter
public class EmailSearchCriteria extends BaseSearchCriteria {
    private EmailStatus status;
}

