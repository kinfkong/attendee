/*
 * Copyright (c) 2018 TopCoder, Inc. All rights reserved.
 */
package com.wiproevents.services;

import com.wiproevents.entities.Poll;
import com.wiproevents.entities.PollTotalResult;
import com.wiproevents.entities.criteria.PollSearchCriteria;
import com.wiproevents.exceptions.AttendeeException;

/**
 * The poll service.Implementation should be effectively thread-safe.
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
public interface PollService extends GenericService<Poll, PollSearchCriteria> {
    PollTotalResult calculatePollTotalResult(String pollId) throws AttendeeException;
}

