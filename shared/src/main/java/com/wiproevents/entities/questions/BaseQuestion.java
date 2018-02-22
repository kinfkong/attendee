package com.wiproevents.entities.questions;

import com.wiproevents.entities.IdentifiableEntity;
import com.wiproevents.entities.types.QuestionType;
import lombok.Getter;
import lombok.Setter;

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
}
