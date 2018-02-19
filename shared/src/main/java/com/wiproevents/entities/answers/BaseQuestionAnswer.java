package com.wiproevents.entities.answers;

import com.wiproevents.entities.AnswerOption;
import com.wiproevents.entities.Model;
import com.wiproevents.entities.briefs.UserBrief;
import com.wiproevents.utils.springdata.extensions.Reference;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * Created by wangjinggang on 2018/2/16.
 */
@Getter
@Setter
public abstract class BaseQuestionAnswer implements Model {
    @Reference
    private UserBrief user;

    @Reference
    private List<AnswerOption> selectedAnswerOptions;
}
