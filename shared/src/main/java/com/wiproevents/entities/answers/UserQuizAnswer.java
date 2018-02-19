package com.wiproevents.entities.answers;

import com.microsoft.azure.spring.data.documentdb.core.mapping.Document;
import com.wiproevents.entities.briefs.QuizBrief;
import com.wiproevents.utils.springdata.extensions.Embed;
import com.wiproevents.utils.springdata.extensions.Reference;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wangjinggang on 2018/2/15.
 */
@Document(collection = "user_quiz_answer")
@Getter
@Setter
public class UserQuizAnswer extends BaseAnswer {
    @Embed
    private List<UserQuizQuestionAnswer> userAnswers = new ArrayList<>();

    @Reference
    private QuizBrief quiz;

    private double score;
    private double totalWeight;
}
