/*
 * Copyright (c) 2018 TopCoder, Inc. All rights reserved.
 */
package com.wiproevents.services.springdata;

import com.microsoft.azure.spring.data.documentdb.core.query.Query;
import com.wiproevents.entities.answers.UserPollAnswer;
import com.wiproevents.entities.criteria.UserPollAnswerSearchCriteria;
import com.wiproevents.utils.Helper;
import com.wiproevents.utils.springdata.extensions.DocumentDbSpecification;
import lombok.AllArgsConstructor;

/**
 * The specification used to query user poll answer by criteria.
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
@AllArgsConstructor
public class UserPollAnswerSpecification implements DocumentDbSpecification<UserPollAnswer> {
    /**
     * The criteria. Final.
     */
    private final UserPollAnswerSearchCriteria criteria;


    /**
     * Converts the search criteria to sql query.
     *
     * @param query the sql query.
     * @return the sql query.
     */
    @Override
    public Query toQuery(Query query) {
        Helper.buildEqualPredict(query, "poll.id", criteria.getPollId());
        return query;
    }
}

