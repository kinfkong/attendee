package com.wiproevents.entities.answers;

import com.wiproevents.entities.questions.SurveyQuestion;
import com.wiproevents.utils.springdata.extensions.Reference;
import lombok.Getter;
import lombok.Setter;

/**
 * Created by wangjinggang on 2018/2/16.
 */
@Getter
@Setter
public class UserSurveyQuestionAnswer extends BaseQuestionAnswer {
    @Reference
    private SurveyQuestion question;
}
