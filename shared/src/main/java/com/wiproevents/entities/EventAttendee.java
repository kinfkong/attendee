package com.wiproevents.entities;

import com.microsoft.azure.spring.data.documentdb.core.mapping.Document;
import com.wiproevents.entities.briefs.EventBrief;
import com.wiproevents.entities.statuses.EventAttendeeStatus;
import com.wiproevents.utils.springdata.extensions.Embed;
import com.wiproevents.utils.springdata.extensions.Reference;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by wangjinggang on 2018/2/19.
 */
@Document(collection = "event_attendee")
@Getter
@Setter
public class EventAttendee extends AuditableUserEntity {

    @Reference
    private User user;

    @Reference
    private EventBrief event;

    private Date registeredOn;

    private EventAttendeeStatus status;

    @Embed
    private List<SouvenirOption> souvenirOptions = new ArrayList<>();
    private String airline;
    private String flightNumber;
    private String spouseAccompanying;
    private String spouseEmail;
    private String accommodationDetails;

    @Reference
    private FileEntity qrCode;

    @Reference
    private FileEntity badge;
}
