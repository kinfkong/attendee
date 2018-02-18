package com.wiproevents.services.springdata;

import com.wiproevents.entities.briefs.SessionBrief;
import com.wiproevents.utils.springdata.extensions.DocumentDbSpecificationRepository;

/**
 * Created by wangjinggang on 2018/2/18.
 */
public interface SessionBriefRepository extends DocumentDbSpecificationRepository<SessionBrief, String> {
}
