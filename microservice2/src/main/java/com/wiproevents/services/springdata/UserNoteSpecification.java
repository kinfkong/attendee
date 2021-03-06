/*
 * Copyright (c) 2018 TopCoder, Inc. All rights reserved.
 */
package com.wiproevents.services.springdata;

import com.microsoft.azure.spring.data.documentdb.core.query.Query;
import com.wiproevents.entities.UserNote;
import com.wiproevents.entities.criteria.UserNoteSearchCriteria;
import com.wiproevents.utils.Helper;
import com.wiproevents.utils.springdata.extensions.DocumentDbSpecification;
import lombok.AllArgsConstructor;

/**
 * The specification used to query user not by criteria.
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
@AllArgsConstructor
public class UserNoteSpecification implements DocumentDbSpecification<UserNote> {
    /**
     * The criteria. Final.
     */
    private final UserNoteSearchCriteria criteria;


    /**
     * Converts the search criteria to sql query.
     *
     * @param query the sql query.
     * @return the sql query.
     */
    @Override
    public Query toQuery(Query query) {
        Helper.buildEqualPredict(query, "event.id", criteria.getEventId());
        Helper.buildEqualPredict(query, "session.id", criteria.getSessionId());
        return query;
    }
}

