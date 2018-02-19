package com.wiproevents.services.springdata;

import com.microsoft.azure.spring.data.documentdb.core.query.Query;
import com.wiproevents.entities.UserEventAssignment;
import com.wiproevents.entities.criteria.UserEventAssignmentSearchCriteria;
import com.wiproevents.utils.Helper;
import com.wiproevents.utils.springdata.extensions.DocumentDbSpecification;
import lombok.AllArgsConstructor;

import java.util.Map;

/**
 * The specification used to query User by criteria.
 */
@AllArgsConstructor
public class UserEventAssignmentSpecification implements DocumentDbSpecification<UserEventAssignment> {
    /**
     * The criteria. Final.
     */
    private final UserEventAssignmentSearchCriteria criteria;

    @Override
    public Query toQuery(Query query) {
        Helper.buildEqualPredict(query, "event.id", criteria.getEventId());
        Helper.buildEqualPredict(query, "user.id", criteria.getUserId());
        return query;
    }
}

