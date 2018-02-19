package com.wiproevents.entities;

import com.microsoft.azure.spring.data.documentdb.core.mapping.Document;
import com.wiproevents.entities.statuses.SessionAttendeeStatus;
import com.wiproevents.utils.springdata.extensions.Reference;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 * Created by wangjinggang on 2018/2/10.
 */
@Document(collection = "session_attendee")
@Getter
@Setter
public class SessionAttendee extends AuditableUserEntity {
    @Reference
    private User user;

    @Reference
    private Session session;

    private Date registeredOn;

    private SessionAttendeeStatus status;
}
