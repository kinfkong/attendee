package com.wiproevents.entities.answers;

import com.wiproevents.entities.IdentifiableEntity;
import com.wiproevents.entities.briefs.UserBrief;
import com.wiproevents.utils.springdata.extensions.Reference;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public abstract class BaseAnswer extends IdentifiableEntity {
    @Reference
    private UserBrief user;
    private boolean completed;
}