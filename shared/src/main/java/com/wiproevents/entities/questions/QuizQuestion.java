package com.wiproevents.entities.questions;

import com.microsoft.azure.spring.data.documentdb.core.mapping.Document;
import lombok.Getter;
import lombok.Setter;

/**
 * Created by wangjinggang on 2018/2/15.
 */
@Getter
@Setter
@Document(collection = "quiz_question")
public class QuizQuestion extends BaseQuestion {
    private double weight;
    private String quizId;
}
