package com.wiproevents.services.springdata;

import com.wiproevents.entities.AnswerOption;
import com.wiproevents.utils.springdata.extensions.DocumentDbSpecificationRepository;

/**
 * Created by wangjinggang on 2018/2/19.
 */
public interface AnswerOptionRepository extends DocumentDbSpecificationRepository<AnswerOption, String> {
}
