package com.wiproevents.services.springdata;

import com.microsoft.azure.spring.data.documentdb.core.query.Query;
import com.wiproevents.entities.User;
import com.wiproevents.entities.criteria.UserSearchCriteria;
import com.wiproevents.utils.Helper;
import com.wiproevents.utils.springdata.extensions.DocumentDbSpecification;
import lombok.AllArgsConstructor;

/**
 * The specification used to query User by criteria.
 */
@AllArgsConstructor
public class UserSpecification implements DocumentDbSpecification<User> {
    /**
     * The criteria. Final.
     */
    private final UserSearchCriteria criteria;


    @Override
    public Query toQuery(Query query) {

        Helper.buildEqualPredict(query, "fullName", this.criteria.getFullName());
        Helper.buildEqualPredict(query, "email", this.criteria.getEmail());
        Helper.buildEqualPredict(query, "status", this.criteria.getStatus());
        return query;
    }
}

