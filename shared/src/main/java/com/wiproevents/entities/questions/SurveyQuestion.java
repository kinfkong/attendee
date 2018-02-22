package com.wiproevents.entities.questions;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.microsoft.azure.spring.data.documentdb.core.mapping.Document;
import com.wiproevents.entities.answers.AnswerOption;
import com.wiproevents.utils.springdata.extensions.AssignId;
import com.wiproevents.utils.springdata.extensions.Reference;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * Created by wangjinggang on 2018/2/15.
 */
@Getter
@Setter
@Document(collection = "survey_question")
public class SurveyQuestion extends BaseQuestion {
    private String surveyId;
    @Reference(cascade = true)
    @AssignId(to = "questionId")
    @JsonIgnoreProperties("rightAnswer")
    private List<AnswerOption> answerOptions;
}
