package com.wiproevents.entities;

import com.microsoft.azure.spring.data.documentdb.core.mapping.Document;
import com.wiproevents.entities.questions.SurveyQuestion;
import com.wiproevents.utils.springdata.extensions.AssignId;
import com.wiproevents.utils.springdata.extensions.Reference;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wangjinggang on 2018/2/16.
 */
@Document(collection = "survey")
@Getter
@Setter
public class Survey extends BaseEngagement {
    @Reference(cascade = true)
    @AssignId(to = "surveyId")
    private List<SurveyQuestion> questions = new ArrayList<>();
}
