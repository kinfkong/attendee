package com.wiproevents.entities.answers;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.wiproevents.entities.questions.SurveyQuestion;
import com.wiproevents.utils.springdata.extensions.Reference;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * Created by wangjinggang on 2018/2/16.
 */
@Getter
@Setter
public class UserSurveyQuestionAnswer extends BaseQuestionAnswer {
    @Reference
    private SurveyQuestion question;

    @Reference
    @JsonIgnoreProperties("rightAnswer")
    private List<AnswerOption> selectedAnswerOptions;
}
