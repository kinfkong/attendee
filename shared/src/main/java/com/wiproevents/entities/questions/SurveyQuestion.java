package com.wiproevents.entities.questions;

import com.microsoft.azure.spring.data.documentdb.core.mapping.Document;
import lombok.Getter;
import lombok.Setter;

/**
 * Created by wangjinggang on 2018/2/15.
 */
@Getter
@Setter
@Document(collection = "survey_question")
public class SurveyQuestion extends BaseQuestion {
    private String surveyId;
}
