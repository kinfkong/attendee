/*
 * Copyright (c) 2018 TopCoder, Inc. All rights reserved.
 */
package com.wiproevents.services.springdata;

import com.wiproevents.entities.Conversation;
import com.wiproevents.entities.criteria.ConversationSearchCriteria;
import com.wiproevents.entities.criteria.MessageSearchCriteria;
import com.wiproevents.entities.statuses.ConversationStatus;
import com.wiproevents.exceptions.AttendeeException;
import com.wiproevents.services.ConversationService;
import com.wiproevents.services.MessageService;
import com.wiproevents.utils.springdata.extensions.DocumentDbSpecification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * The Spring Data JPA implementation of ConversationService,
 * extends BaseService<Conversation, ConversationSearchCriteria>. Effectively thread safe after configuration.
 */
@Service
public class ConversationServiceImpl
        extends BaseService<Conversation, ConversationSearchCriteria> implements ConversationService {

    @Autowired
    private MessageService messageService;

    /**
     * This method is used to get the specification.
     *
     * @param criteria the search criteria
     * @return the specification
     * @throws AttendeeException if any other error occurred during operation
     */
    @Override
    protected DocumentDbSpecification<Conversation> getSpecification(ConversationSearchCriteria criteria)
            throws AttendeeException {
        return new ConversationSpecification(criteria);
    }

    @Override
    protected void handleNestedCreate(Conversation entity) throws AttendeeException {
        super.handleNestedCreate(entity);
        entity.setStatus(ConversationStatus.Active);
    }

    @Override
    protected void handlePopulate(Conversation entity) throws AttendeeException {
        super.handlePopulate(entity);
        // get the messages of the conversation
        MessageSearchCriteria criteria = new MessageSearchCriteria();
        criteria.setConversationId(criteria.getConversationId());
        entity.setMessages(messageService.search(criteria, null).getEntities());

    }
}

