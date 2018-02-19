package com.wiproevents.services.springdata;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * Created by wangjinggang on 2018/2/8.
 */
@Component
public class RepositoryRelation {

    @Autowired
    private FileEntityRepository fileEntityRepository;


    @Autowired
    private EventBriefRepository eventBriefRepository;


    @Autowired
    private NotificationTypeRepository notificationTypeRepository;

    @Autowired
    private UserNoteRepository userNoteRepository;

    @Autowired
    private SessionBriefRepository sessionBriefRepository;

    @Autowired
    private NotificationRepository notificationRepository;

    @Autowired
    private TimezoneRepository timezoneRepository;

    @Autowired
    private EmailRepository emailRepository;

    @Autowired
    private ConversationRepository conversationRepository;

    @Autowired
    private UserBriefRepository userBriefRepository;

    @Autowired
    private MessageRepository messageRepository;

    @Autowired
    private EventWallPostRepository eventWallPostRepository;

    @PostConstruct
    public void handleRepositoryRelations() {
        // handleUserNoteRepository();
        // handleNotificationRepository();
        // handleEmailRepository();
        handleConversationRepository();
        // handleMessageRepository();
        handleEventWallPostRepository();
    }

    private void handleEventWallPostRepository() {
        eventWallPostRepository.addNestedRepository("user", userBriefRepository);
        eventWallPostRepository.addNestedRepository("attachedFiles", fileEntityRepository);
        eventWallPostRepository.addNestedRepository("likeFromUsers", userBriefRepository);
    }

    private void handleMessageRepository() {
        messageRepository.addNestedRepository("sender", userBriefRepository);
        messageRepository.addNestedRepository("attachedFiles", fileEntityRepository);
    }

    private void handleUserNoteRepository() {
        userNoteRepository.addNestedRepository("event", eventBriefRepository);
        userNoteRepository.addNestedRepository("session", sessionBriefRepository);
        userNoteRepository.addNestedRepository("attachedFiles", fileEntityRepository);
    }

    private void handleNotificationRepository() {
        notificationRepository.addNestedRepository("type", notificationTypeRepository);
    }

    private void handleEmailRepository() {
        emailRepository.addNestedRepository("timezone", timezoneRepository);
    }

    private void handleConversationRepository() {
        conversationRepository.addNestedRepository("participants", userBriefRepository);
        conversationRepository.addNestedRepository("session", sessionBriefRepository);
    }
}
