package com.wiproevents.entities.briefs;

import com.microsoft.azure.spring.data.documentdb.core.mapping.Document;
import com.wiproevents.entities.IdentifiableEntity;
import lombok.Getter;
import lombok.Setter;

/**
 * Created by wangjinggang on 2018/2/16.
 */
@Document(collection = "poll")
@Getter
@Setter
public class PollBrief extends IdentifiableEntity {
    private String name;
}
