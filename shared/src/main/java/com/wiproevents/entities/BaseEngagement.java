package com.wiproevents.entities;

import com.wiproevents.entities.briefs.EventBrief;
import com.wiproevents.entities.briefs.SessionBrief;
import com.wiproevents.entities.statuses.EngagementStatus;
import com.wiproevents.entities.types.EngagementContextType;
import com.wiproevents.entities.types.EngagementType;
import com.wiproevents.utils.springdata.extensions.ForeignKey;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.NotBlank;

import java.util.Date;

/**
 * Created by wangjinggang on 2018/2/17.
 */
@Getter
@Setter
public abstract class BaseEngagement extends IdentifiableEntity {
    private String name;
    private EngagementContextType context;

    @ForeignKey(clazz = EventBrief.class)
    @NotBlank
    private String eventId;

    @ForeignKey(clazz = SessionBrief.class)
    private String sessionId;

    private String description;
    private EngagementStatus status;
    private EngagementType type;
    private Date startTime;
    private Date endTime;
}
