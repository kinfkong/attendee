package com.wiproevents.entities.answers;

import com.wiproevents.entities.questions.PollQuestion;
import com.wiproevents.utils.springdata.extensions.Reference;
import lombok.Getter;
import lombok.Setter;

/**
 * Created by wangjinggang on 2018/2/16.
 */
@Getter
@Setter
public class UserPollQuestionAnswer extends BaseQuestionAnswer {
    @Reference
    private PollQuestion question;
}
