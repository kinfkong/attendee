package com.wiproevents.services;

import com.wiproevents.entities.IdentifiableEntity;
import com.wiproevents.exceptions.AttendeeException;

/**
 * Created by wangjinggang on 2018/2/19.
 */
public interface AnnotationHandlerInterface {
    IdentifiableEntity populate(IdentifiableEntity entity) throws AttendeeException;

    IdentifiableEntity upsert(IdentifiableEntity entity, IdentifiableEntity oldEntity)
            throws AttendeeException;
}
