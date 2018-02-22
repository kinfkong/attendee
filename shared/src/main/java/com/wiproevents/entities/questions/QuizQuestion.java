package com.wiproevents.entities.questions;

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
@Document(collection = "quiz_question")
public class QuizQuestion extends BaseQuestion {
    private double weight;
    private String quizId;

    @Reference(cascade = true)
    @AssignId(to = "questionId")
    private List<AnswerOption> answerOptions;
}
