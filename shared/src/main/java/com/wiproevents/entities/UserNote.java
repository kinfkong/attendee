package com.wiproevents.entities;

import com.microsoft.azure.spring.data.documentdb.core.mapping.Document;
import com.wiproevents.entities.briefs.EventBrief;
import com.wiproevents.entities.briefs.SessionBrief;
import com.wiproevents.entities.briefs.UserBrief;
import com.wiproevents.utils.springdata.extensions.ForeignKey;
import com.wiproevents.utils.springdata.extensions.Reference;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by wangjinggang on 2018/2/16.
 */
@Getter
@Setter
@Document(collection = "user_note")
public class UserNote extends AuditableEntity {

    @ForeignKey(clazz = UserBrief.class)
    @NotBlank
    private String userId;

    @Reference
    @NotNull
    @Valid
    private SessionBrief session;

    @Reference
    @NotNull
    @Valid
    private EventBrief event;

    @NotBlank
    private String text;

    @Reference
    @Valid
    private List<FileEntity> attachedFiles = new ArrayList<>();
}
