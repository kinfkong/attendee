/*
 * Copyright (c) 2018 TopCoder, Inc. All rights reserved.
 */
package com.wiproevents.services.springdata;

import com.wiproevents.entities.EventCategory;
import com.wiproevents.utils.springdata.extensions.DocumentDbSpecificationRepository;
import org.springframework.stereotype.Repository;

/**
 * The event category repository.
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
@Repository
public interface EventCategoryRepository extends DocumentDbSpecificationRepository<EventCategory, String> {
}

