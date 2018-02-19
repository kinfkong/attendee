package com.wiproevents.services.springdata;

import com.wiproevents.entities.questions.SurveyQuestion;
import com.wiproevents.utils.springdata.extensions.DocumentDbSpecificationRepository;

/**
 * Created by wangjinggang on 2018/2/19.
 */
public interface SurveyQuestionRepository extends DocumentDbSpecificationRepository<SurveyQuestion, String> {
}
