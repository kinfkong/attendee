package com.wiproevents.services.springdata;

import com.microsoft.azure.spring.data.documentdb.core.query.Query;
import com.wiproevents.entities.Event;
import com.wiproevents.entities.criteria.EventSearchCriteria;
import com.wiproevents.utils.Helper;
import com.wiproevents.utils.springdata.extensions.DocumentDbSpecification;
import lombok.AllArgsConstructor;

import java.util.Map;

/**
 * The specification used to query User by criteria.
 */
@AllArgsConstructor
public class EventSpecification implements DocumentDbSpecification<Event> {
    /**
     * The criteria. Final.
     */
    private final EventSearchCriteria criteria;


    @Override
    public Query toQuery(Query query) {
        Helper.buildEqualPredict(query,  "name", this.criteria.getName());
        return query;
    }
}

