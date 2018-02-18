package com.wiproevents.entities;

import com.microsoft.azure.spring.data.documentdb.core.mapping.Document;
import lombok.Getter;
import lombok.Setter;

/**
 * Created by wangjinggang on 2018/2/7.
 */
@Getter
@Setter
@Document(collection = "answer_option")
public class AnswerOption extends IdentifiableEntity {
    private String questionId;
    private String text;
    private int orderNumber;
}
