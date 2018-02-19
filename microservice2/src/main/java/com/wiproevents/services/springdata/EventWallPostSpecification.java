package com.wiproevents.services.springdata;

import com.microsoft.azure.spring.data.documentdb.core.query.Query;
import com.wiproevents.entities.EventWallPost;
import com.wiproevents.entities.criteria.EventWallPostSearchCriteria;
import com.wiproevents.utils.Helper;
import com.wiproevents.utils.springdata.extensions.DocumentDbSpecification;
import lombok.AllArgsConstructor;

/**
 * The specification used to query User by criteria.
 */
@AllArgsConstructor
public class EventWallPostSpecification implements DocumentDbSpecification<EventWallPost> {
    /**
     * The criteria. Final.
     */
    private final EventWallPostSearchCriteria criteria;


    @Override
    public Query toQuery(Query query) {
        Helper.buildEqualPredict(query,  "parentPostId", criteria.getParentPostId());
        Helper.buildEqualPredict(query, "eventId", criteria.getEventId());
        return query;
    }
}

