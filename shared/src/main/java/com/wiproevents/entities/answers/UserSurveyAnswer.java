package com.wiproevents.entities.answers;

import com.microsoft.azure.spring.data.documentdb.core.mapping.Document;
import com.wiproevents.entities.briefs.SurveyBrief;
import com.wiproevents.utils.springdata.extensions.Embed;
import com.wiproevents.utils.springdata.extensions.Reference;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;


@Document(collection = "user_survey_answer")
@Getter
@Setter
public class UserSurveyAnswer extends BaseAnswer {
    @Reference
    private SurveyBrief survey;

    @Embed
    private List<UserSurveyQuestionAnswer> userAnswers = new ArrayList<>();
}
