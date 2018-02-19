package com.wiproevents.entities;

import com.microsoft.azure.spring.data.documentdb.core.mapping.Document;
import com.wiproevents.entities.briefs.EventBrief;
import com.wiproevents.utils.springdata.extensions.Embed;
import com.wiproevents.utils.springdata.extensions.Reference;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by wangjinggang on 2018/2/7.
 */
@Document(collection = "event_session")
@Getter
@Setter
public class Session extends AuditableUserEntity {
    @Reference
    private EventBrief event;
    private String dayAgendaId;
    private String name;
    private String venue;
    private String building;
    private String rooms;
    @Reference
    private FileEntity mapImage;
    private Date startTime;
    private Date endTime;
    private int seatCapability;

    @Reference
    private List<User> assignedSpeakers = new ArrayList<>();

    @Embed
    private List<Speaker> addedSpeakers = new ArrayList<>();
    @Reference
    private List<FileEntity> galleryImages = new ArrayList<>();
    @Embed
    private List<SessionFile> files = new ArrayList<>();

    private SessionStatus status;
}
