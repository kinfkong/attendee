package com.wiproevents.entities.answers;

import com.wiproevents.entities.IdentifiableEntity;
import com.wiproevents.entities.briefs.UserBrief;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public abstract class BaseAnswer extends IdentifiableEntity {
    private UserBrief user;
    private boolean completed;
}