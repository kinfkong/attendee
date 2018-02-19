package com.wiproevents.entities;

import com.microsoft.azure.spring.data.documentdb.core.mapping.Document;
import com.sun.istack.internal.NotNull;
import com.wiproevents.entities.briefs.UserBrief;
import com.wiproevents.entities.statuses.MessageStatus;
import com.wiproevents.utils.springdata.extensions.ForeignKey;
import com.wiproevents.utils.springdata.extensions.Reference;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.NotBlank;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wangjinggang on 2018/2/16.
 */
@Getter
@Setter
@Document(collection = "message")
public class Message extends AuditableUserEntity {
    @NotBlank
    @ForeignKey(clazz = Conversation.class)
    private String conversationId;

    @NotNull
    @Reference
    private UserBrief sender;

    @NotBlank
    private String text;

    private MessageStatus status;

    @Reference
    private List<FileEntity> attachedFiles = new ArrayList<>();
}
