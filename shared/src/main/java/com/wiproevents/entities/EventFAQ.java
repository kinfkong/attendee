package com.wiproevents.entities;

import com.microsoft.azure.spring.data.documentdb.core.mapping.Document;
import com.wiproevents.entities.answers.FAQQuestionAnswer;
import com.wiproevents.utils.springdata.extensions.AssignId;
import com.wiproevents.utils.springdata.extensions.ForeignKey;
import com.wiproevents.utils.springdata.extensions.Reference;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wangjinggang on 2018/2/16.
 */
@Document(collection = "event_faq")
@Getter
@Setter
public class EventFAQ extends IdentifiableEntity {
    @ForeignKey(clazz = Event.class)
    private String eventId;

    @Reference(cascade = true)
    @AssignId(to = "eventId", from = "eventId")
    private List<FAQQuestionAnswer> questionAnswers = new ArrayList<>();
}
