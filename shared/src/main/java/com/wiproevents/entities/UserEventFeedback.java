package com.wiproevents.entities;

import com.microsoft.azure.spring.data.documentdb.core.mapping.Document;
import com.wiproevents.entities.answers.UserSurveyAnswer;
import com.wiproevents.entities.briefs.EventBrief;
import com.wiproevents.entities.briefs.UserBrief;
import com.wiproevents.utils.springdata.extensions.Reference;
import lombok.Getter;
import lombok.Setter;

/**
 * Created by wangjinggang on 2018/2/16.
 */
@Getter
@Setter
@Document(collection = "user_event_feedback")
public class UserEventFeedback extends AuditableEntity {
    @Reference
    private EventBrief event;

    @Reference
    private UserBrief user;

    @Reference(cascade = true)
    private UserSurveyAnswer feedback;

    private int eventRating;
    private String comment;
    private boolean completed;
}
