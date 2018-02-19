package com.wiproevents.utils.springdata.extensions;

import com.wiproevents.entities.IdentifiableEntity;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Created by wangjinggang on 2018/2/19.
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface ForeignKey {
    Class<? extends IdentifiableEntity> clazz();
}
