package com.wiproevents.services.springdata;

import com.microsoft.azure.spring.data.documentdb.core.query.Query;
import com.wiproevents.entities.UserRole;
import com.wiproevents.entities.criteria.UserRoleSearchCriteria;
import com.wiproevents.utils.Helper;
import com.wiproevents.utils.springdata.extensions.DocumentDbSpecification;
import lombok.AllArgsConstructor;

/**
 * The specification used to query User by criteria.
 */
@AllArgsConstructor
public class UserRoleSpecification implements DocumentDbSpecification<UserRole> {
    /**
     * The criteria. Final.
     */
    private final UserRoleSearchCriteria criteria;


    @Override
    public Query toQuery(Query query) {
        Helper.buildEqualPredict(query, "name", criteria.getName());
        return query;
    }
}

