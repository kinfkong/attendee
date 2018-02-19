package com.wiproevents.utils.springdata.extensions;

import com.microsoft.azure.spring.data.documentdb.core.query.CriteriaDefinition;

/**
 * Created by wangjinggang on 2018/2/19.
 */
public class InCriteria implements CriteriaDefinition {
    private final String key;
    private final Object value;
    public InCriteria(String key, Object value) {
        this.key = key;
        this.value = value;
    }

    @Override
    public Object getCriteriaObject() {
        return value;
    }

    @Override
    public String getKey() {
        return key;
    }
}
