/*
 * Copyright (c) 2018 TopCoder, Inc. All rights reserved.
 */
package com.wiproevents.entities.criteria;

import com.wiproevents.entities.types.EngagementContextType;
import lombok.Getter;
import lombok.Setter;


/**
 * The survey search criteria.
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
@Getter
@Setter
public class SurveySearchCriteria extends BaseSearchCriteria {
    private String sessionId;
    private String eventId;
    private String name;
    private EngagementContextType context;
}

