package com.wiproevents.entities;

import com.microsoft.azure.spring.data.documentdb.core.mapping.Document;
import com.wiproevents.entities.briefs.UserBrief;
import com.wiproevents.entities.statuses.NotificationStatus;
import com.wiproevents.entities.types.NotificationType;
import com.wiproevents.utils.springdata.extensions.ForeignKey;
import com.wiproevents.utils.springdata.extensions.Reference;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * Created by wangjinggang on 2018/2/16.
 */
@Document(collection = "notification")
@Getter
@Setter
public class Notification extends IdentifiableEntity {

    @ForeignKey(clazz = UserBrief.class)
    @NotBlank
    private String userId;

    @NotBlank
    private String title;

    @NotBlank
    private String text;

    @Reference
    @NotNull
    private NotificationType type;

    private NotificationStatus status;

    private Date createdOn;

    private Date readOn;
}
