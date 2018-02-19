package com.wiproevents.entities.answers;

import com.wiproevents.entities.questions.QuizQuestion;
import com.wiproevents.utils.springdata.extensions.Reference;
import lombok.Getter;
import lombok.Setter;

/**
 * Created by wangjinggang on 2018/2/16.
 */
@Getter
@Setter
public class UserQuizQuestionAnswer extends BaseQuestionAnswer {
    @Reference
    private QuizQuestion question;
    private boolean answerCorrect;
}
