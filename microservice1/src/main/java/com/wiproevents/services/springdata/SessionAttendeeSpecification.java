package com.wiproevents.services.springdata;

import com.microsoft.azure.spring.data.documentdb.core.query.Query;
import com.wiproevents.entities.SessionAttendee;
import com.wiproevents.entities.criteria.SessionAttendeeSearchCriteria;
import com.wiproevents.utils.Helper;
import com.wiproevents.utils.springdata.extensions.DocumentDbSpecification;
import lombok.AllArgsConstructor;

import java.util.Map;

/**
 * The specification used to query User by criteria.
 */
@AllArgsConstructor
public class SessionAttendeeSpecification implements DocumentDbSpecification<SessionAttendee> {
    /**
     * The criteria. Final.
     */
    private final SessionAttendeeSearchCriteria criteria;


    @Override
    public Query toQuery(Query query) {
        Helper.buildEqualPredict(query, "session.id", this.criteria.getSessionId());
        Helper.buildEqualPredict(query, "user.id", this.criteria.getUserId());
        Helper.buildEqualPredict(query, "status", this.criteria.getStatus());
        return query;
    }
}

