package com.wiproevents.services.springdata;

import com.microsoft.azure.spring.data.documentdb.core.query.Query;
import com.wiproevents.entities.Session;
import com.wiproevents.entities.criteria.SessionSearchCriteria;
import com.wiproevents.utils.Helper;
import com.wiproevents.utils.springdata.extensions.DocumentDbSpecification;
import lombok.AllArgsConstructor;

import java.util.Map;

/**
 * The specification used to query User by criteria.
 */
@AllArgsConstructor
public class SessionSpecification implements DocumentDbSpecification<Session> {
    /**
     * The criteria. Final.
     */
    private final SessionSearchCriteria criteria;


    @Override
    public Query toQuery(Query query) {
        Helper.buildEqualPredict(query, "dayAgendaId", criteria.getDayAgendaId());
        Helper.buildEqualPredict(query, "event.id", criteria.getEventId());
        Helper.buildEqualPredict(query, "name", criteria.getName());
        return query;
    }
}

