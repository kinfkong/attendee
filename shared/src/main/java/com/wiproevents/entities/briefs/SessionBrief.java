package com.wiproevents.entities.briefs;

import com.microsoft.azure.spring.data.documentdb.core.mapping.Document;
import com.wiproevents.entities.IdentifiableEntity;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 * Created by wangjinggang on 2018/2/16.
 */
@Document(collection = "event_session")
@Getter
@Setter
public class SessionBrief extends IdentifiableEntity {
    private String name;
    private Date startTime;
    private Date endTime;
}
