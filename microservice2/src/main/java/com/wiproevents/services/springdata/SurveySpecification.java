/*
 * Copyright (c) 2018 TopCoder, Inc. All rights reserved.
 */
package com.wiproevents.services.springdata;

import com.microsoft.azure.spring.data.documentdb.core.query.Query;
import com.wiproevents.entities.Survey;
import com.wiproevents.entities.criteria.SurveySearchCriteria;
import com.wiproevents.utils.Helper;
import com.wiproevents.utils.springdata.extensions.DocumentDbSpecification;
import lombok.AllArgsConstructor;

/**
 * The specification used to query survey by criteria.
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
@AllArgsConstructor
public class SurveySpecification implements DocumentDbSpecification<Survey> {
    /**
     * The criteria. Final.
     */
    private final SurveySearchCriteria criteria;


    /**
     * Converts the search criteria to sql query.
     *
     * @param query the sql query.
     * @return the sql query.
     */
    @Override
    public Query toQuery(Query query) {
        Helper.buildEqualPredict(query, "sessionId", criteria.getSessionId());
        Helper.buildEqualPredict(query, "eventId", criteria.getEventId());
        Helper.buildEqualPredict(query, "context", criteria.getContext());
        Helper.buildEqualPredict(query, "name", criteria.getName());
        return query;
    }
}

