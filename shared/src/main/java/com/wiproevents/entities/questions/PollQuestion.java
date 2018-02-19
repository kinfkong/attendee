package com.wiproevents.entities.questions;

import com.microsoft.azure.spring.data.documentdb.core.mapping.Document;
import lombok.Getter;
import lombok.Setter;

/**
 * Created by wangjinggang on 2018/2/15.
 */
@Document(collection = "poll_question")
@Getter
@Setter
public class PollQuestion extends BaseQuestion {
    private String pollId;
}
