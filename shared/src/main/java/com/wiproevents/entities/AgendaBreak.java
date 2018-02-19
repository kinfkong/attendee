package com.wiproevents.entities;

import com.wiproevents.utils.springdata.extensions.Reference;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 * Created by wangjinggang on 2018/2/7.
 */
@Getter
@Setter
public class AgendaBreak extends AuditableUserEntity {
    private String name;
    private String venue;
    private String building;
    private String rooms;
    @Reference
    private FileEntity mapImage;
    private Date startTime;
    private Date endTime;
}
