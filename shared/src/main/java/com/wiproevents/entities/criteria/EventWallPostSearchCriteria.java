package com.wiproevents.entities.criteria;

import lombok.Getter;
import lombok.Setter;


/**
 * The user search criteria.
 */
@Getter
@Setter
public class EventWallPostSearchCriteria extends BaseSearchCriteria {
    private String eventId;
}

