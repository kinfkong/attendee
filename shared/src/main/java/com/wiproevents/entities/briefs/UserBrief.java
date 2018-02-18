package com.wiproevents.entities.briefs;

import com.microsoft.azure.spring.data.documentdb.core.mapping.Document;
import com.wiproevents.entities.IdentifiableEntity;
import lombok.Getter;
import lombok.Setter;

/**
 * Created by wangjinggang on 2018/2/16.
 */
@Document(collection = "user")
@Getter
@Setter
public class UserBrief extends IdentifiableEntity {
    private String fullName;
    private String email;
}
