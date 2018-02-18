package com.wiproevents.entities;

import com.microsoft.azure.spring.data.documentdb.core.mapping.Document;
import com.wiproevents.entities.briefs.UserBrief;
import com.wiproevents.entities.statuses.EventWallPostStatus;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wangjinggang on 2018/2/16.
 */
@Getter
@Setter
@Document(collection = "event_wall_post")
public class EventWallPost extends AuditableEntity {
    private String parentPostId;
    private UserBrief user;
    private String text;
    private EventWallPostStatus status;
    private List<FileEntity> attachedFiles = new ArrayList<>();
    private List<EventWallPost> childPosts = new ArrayList<>();
    private List<UserBrief> likeFromUsers = new ArrayList<>();
}
