package com.wiproevents.entities;

import com.microsoft.azure.spring.data.documentdb.core.mapping.Document;
import com.wiproevents.entities.briefs.SessionBrief;
import com.wiproevents.entities.briefs.UserBrief;
import com.wiproevents.entities.statuses.ConversationStatus;
import com.wiproevents.entities.types.ConversationType;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wangjinggang on 2018/2/16.
 */
@Document(collection = "conversation")
@Getter
@Setter
public class Conversation extends AuditableUserEntity {
    private List<UserBrief> participants = new ArrayList<>();
    private List<Message> messages = new ArrayList<>();
    private ConversationStatus status;
    private ConversationType type;
    private SessionBrief session;
}
