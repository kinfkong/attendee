/*
 * Copyright (c) 2018 TopCoder, Inc. All rights reserved.
 */
package com.wiproevents.services.springdata;

import com.microsoft.azure.spring.data.documentdb.core.query.Query;
import com.wiproevents.entities.answers.UserQuizAnswer;
import com.wiproevents.entities.criteria.UserQuizAnswerSearchCriteria;
import com.wiproevents.utils.Helper;
import com.wiproevents.utils.springdata.extensions.DocumentDbSpecification;
import lombok.AllArgsConstructor;

/**
 * The specification used to query user quiz answer by criteria.
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
@AllArgsConstructor
public class UserQuizAnswerSpecification implements DocumentDbSpecification<UserQuizAnswer> {
    /**
     * The criteria. Final.
     */
    private final UserQuizAnswerSearchCriteria criteria;


    /**
     * Converts the search criteria to sql query.
     *
     * @param query the sql query.
     * @return the sql query.
     */
    @Override
    public Query toQuery(Query query) {
        Helper.buildEqualPredict(query, "user.id", criteria.getUserId());
        Helper.buildEqualPredict(query, "quiz.id", criteria.getQuizId());
        return query;
    }
}

