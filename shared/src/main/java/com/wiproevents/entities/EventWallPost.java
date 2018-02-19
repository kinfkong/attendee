package com.wiproevents.entities;

import com.microsoft.azure.spring.data.documentdb.core.mapping.Document;
import com.wiproevents.entities.briefs.EventBrief;
import com.wiproevents.entities.briefs.UserBrief;
import com.wiproevents.entities.statuses.EventWallPostStatus;
import com.wiproevents.utils.springdata.extensions.ForeignKey;
import com.wiproevents.utils.springdata.extensions.Reference;
import com.wiproevents.utils.springdata.extensions.ReverseReference;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by wangjinggang on 2018/2/16.
 */
@Getter
@Setter
@Document(collection = "event_wall_post")
public class EventWallPost extends AuditableEntity {

    @ForeignKey(clazz = EventWallPost.class)
    private String parentPostId;

    @ForeignKey(clazz = EventBrief.class)
    @NotBlank
    private String eventId;

    @Reference
    @NotNull
    private UserBrief user;

    @NotBlank
    private String text;

    private EventWallPostStatus status;

    @Reference
    private List<FileEntity> attachedFiles = new ArrayList<>();

    @ReverseReference(by = "parentPostId")
    private List<EventWallPost> childPosts = new ArrayList<>();

    @Reference
    private List<UserBrief> likeFromUsers = new ArrayList<>();
}
