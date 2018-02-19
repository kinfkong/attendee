package com.wiproevents.entities.answers;

import com.microsoft.azure.spring.data.documentdb.core.mapping.Document;
import com.wiproevents.entities.AuditableUserEntity;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
@Document(collection = "faq_question_answer")
public class FAQQuestionAnswer extends AuditableUserEntity {
    private String eventId;
    private String questionText;
    private String answerText;
    private int orderNumber;
}
