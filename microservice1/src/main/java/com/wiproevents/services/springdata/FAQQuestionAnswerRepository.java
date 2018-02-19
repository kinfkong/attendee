package com.wiproevents.services.springdata;

import com.wiproevents.entities.answers.FAQQuestionAnswer;
import com.wiproevents.utils.springdata.extensions.DocumentDbSpecificationRepository;
import org.springframework.stereotype.Repository;

/**
 * The User repository.
 */
@Repository
public interface FAQQuestionAnswerRepository extends DocumentDbSpecificationRepository<FAQQuestionAnswer, String> {
}

