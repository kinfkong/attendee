package com.wiproevents.entities;

import com.microsoft.azure.spring.data.documentdb.core.mapping.Document;
import com.wiproevents.entities.briefs.EventBrief;
import com.wiproevents.entities.briefs.SessionBrief;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wangjinggang on 2018/2/16.
 */
@Getter
@Setter
@Document(collection = "user_note")
public class UserNote extends AuditableEntity {
    private String userId;
    private SessionBrief session;
    private EventBrief event;
    private String text;
    private List<FileEntity> attachedFiles = new ArrayList<>();
}
