package com.wiproevents.services;

import com.wiproevents.entities.Session;
import com.wiproevents.entities.criteria.SessionSearchCriteria;

/**
 * The lookup service implementation should be effectively thread-safe.
 */
public interface SessionService extends GenericService<Session, SessionSearchCriteria> {
}

