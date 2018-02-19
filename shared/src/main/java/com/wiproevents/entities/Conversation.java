package com.wiproevents.entities;

import com.microsoft.azure.spring.data.documentdb.core.mapping.Document;
import com.wiproevents.entities.briefs.SessionBrief;
import com.wiproevents.entities.briefs.UserBrief;
import com.wiproevents.entities.statuses.ConversationStatus;
import com.wiproevents.entities.types.ConversationType;
import com.wiproevents.utils.springdata.extensions.Reference;
import com.wiproevents.utils.springdata.extensions.ReverseReference;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by wangjinggang on 2018/2/16.
 */
@Document(collection = "conversation")
@Getter
@Setter
public class Conversation extends AuditableUserEntity {
    @Reference
    private List<UserBrief> participants = new ArrayList<>();

    @ReverseReference(by = "conversationId")
    private List<Message> messages = new ArrayList<>();

    private ConversationStatus status;

    @NotNull
    private ConversationType type;

    @Reference
    private SessionBrief session;
}
