package com.wiproevents.entities.questions;

import com.wiproevents.entities.AnswerOption;
import com.wiproevents.entities.IdentifiableEntity;
import com.wiproevents.entities.types.QuestionType;
import com.wiproevents.utils.springdata.extensions.Reference;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

import static com.wiproevents.entities.types.QuestionType.SingleSelection;

/**
 * Created by wangjinggang on 2018/2/15.
 */
@Getter
@Setter
public abstract class BaseQuestion extends IdentifiableEntity {
    private String text;
    private int orderNumber;
    private QuestionType type = SingleSelection;

    @Reference(cascade = true, assignIdTo = "questionId")
    private List<AnswerOption> answerOptions;
}
