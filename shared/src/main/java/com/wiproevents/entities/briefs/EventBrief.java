package com.wiproevents.entities.briefs;

import com.microsoft.azure.spring.data.documentdb.core.mapping.Document;
import com.wiproevents.entities.IdentifiableEntity;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 * Created by wangjinggang on 2018/2/7.
 */
@Getter
@Setter
@Document(collection = "event")
public class EventBrief extends IdentifiableEntity {
    private String name;
    private Date startDate;
    private Date endDate;
}
