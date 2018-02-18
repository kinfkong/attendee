package com.wiproevents.entities;

import com.microsoft.azure.spring.data.documentdb.core.mapping.Document;
import com.wiproevents.entities.statuses.NotificationStatus;
import com.wiproevents.entities.types.NotificationType;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 * Created by wangjinggang on 2018/2/16.
 */
@Document(collection = "notification")
@Getter
@Setter
public class Notification extends IdentifiableEntity {
    private String userId;
    private String title;
    private String text;
    private NotificationType type;
    private NotificationStatus status;
    private Date createdOn;
    private Date readOn;
}
