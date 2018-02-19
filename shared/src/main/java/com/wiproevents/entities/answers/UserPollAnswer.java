package com.wiproevents.entities.answers;

import com.microsoft.azure.spring.data.documentdb.core.mapping.Document;
import com.wiproevents.entities.briefs.PollBrief;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wangjinggang on 2018/2/15.
 */
@Document(collection = "user_poll_answer")
@Getter
@Setter
public class UserPollAnswer extends BaseAnswer {
    private List<UserPollQuestionAnswer> userAnswers = new ArrayList<>();
    private PollBrief poll;
}
