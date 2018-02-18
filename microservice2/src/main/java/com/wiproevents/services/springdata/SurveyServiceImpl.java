/*
 * Copyright (c) 2018 TopCoder, Inc. All rights reserved.
 */
package com.wiproevents.services.springdata;

import com.wiproevents.entities.IdentifiableEntity;
import com.wiproevents.entities.Survey;
import com.wiproevents.entities.criteria.SurveySearchCriteria;
import com.wiproevents.exceptions.AttendeeException;
import com.wiproevents.services.SurveyService;
import com.wiproevents.utils.springdata.extensions.DocumentDbSpecification;
import com.wiproevents.utils.springdata.extensions.DocumentDbSpecificationRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * The Spring Data JPA implementation of SurveyService,
 * extends BaseService<Survey, SurveySearchCriteria>. Effectively thread safe after configuration.
 */
@Service
public class SurveyServiceImpl
        extends BaseService<Survey, SurveySearchCriteria> implements SurveyService {

    /**
     * This method is used to get the specification.
     *
     * @param criteria the search criteria
     * @return the specification
     * @throws AttendeeException if any other error occurred during operation
     */
    @Override
    protected DocumentDbSpecification<Survey> getSpecification(SurveySearchCriteria criteria)
            throws AttendeeException {
        return new SurveySpecification(criteria);
    }

    @Override
    protected void handleNestedUpdate(Survey entity, Survey oldEntity) throws AttendeeException {
        super.handleNestedUpdate(entity, oldEntity);

    }

    private <T extends IdentifiableEntity>
    void handleListReferenceUpdate(List<T> newList, List<T> oldList, Class<T> clazz) {

        // get repository by entity type
        DocumentDbSpecificationRepository<T, String> repository = null;
        List<T> toAdd = new ArrayList<>();
        List<T> toUpdate = new ArrayList<>();
        List<T> toDelete = new ArrayList<>();



    }

    private void handleEntityReferenceUpdate(IdentifiableEntity entity, IdentifiableEntity oldEntity) {

    }
}

