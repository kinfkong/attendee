/*
 * Copyright (c) 2018 TopCoder, Inc. All rights reserved.
 */
package com.wiproevents.services.springdata;

import com.microsoft.azure.spring.data.documentdb.core.query.Query;
import com.wiproevents.entities.EventNotification;
import com.wiproevents.entities.criteria.EventNotificationSearchCriteria;
import com.wiproevents.utils.springdata.extensions.DocumentDbSpecification;
import lombok.AllArgsConstructor;

/**
 * The specification used to query event notification by criteria.
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
@AllArgsConstructor
public class EventNotificationSpecification implements DocumentDbSpecification<EventNotification> {
    /**
     * The criteria. Final.
     */
    private final EventNotificationSearchCriteria criteria;


    /**
     * Converts the search criteria to sql query.
     *
     * @param query the sql query.
     * @return the sql query.
     */
    @Override
    public Query toQuery(Query query) {
        return query;
    }
}

