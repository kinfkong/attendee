package com.wiproevents.entities;

import com.microsoft.azure.spring.data.documentdb.core.mapping.Document;
import com.wiproevents.entities.briefs.EventBrief;
import com.wiproevents.entities.types.NotificationType;
import com.wiproevents.entities.types.RecipientType;
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
@Document(collection = "event_notification")
@Getter
@Setter
public class EventNotification extends AuditableUserEntity {
    @ForeignKey(clazz = EventBrief.class)
    @NotBlank
    private String eventId;

    @NotBlank
    private String title;

    @NotBlank
    private String text;

    private List<RecipientType> recipientTypes = new ArrayList<>();

    @Reference
    private NotificationType type;
}
