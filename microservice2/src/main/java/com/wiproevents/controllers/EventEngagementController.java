/*
 * Copyright (c) 2018 TopCoder, Inc. All rights reserved.
 */
package com.wiproevents.controllers;


import com.wiproevents.entities.Engagement;
import com.wiproevents.entities.Poll;
import com.wiproevents.entities.Quiz;
import com.wiproevents.entities.Survey;
import com.wiproevents.entities.criteria.PollSearchCriteria;
import com.wiproevents.entities.criteria.QuizSearchCriteria;
import com.wiproevents.entities.criteria.SurveySearchCriteria;
import com.wiproevents.exceptions.AttendeeException;
import com.wiproevents.exceptions.EntityNotFoundException;
import com.wiproevents.services.PollService;
import com.wiproevents.services.QuizService;
import com.wiproevents.services.SurveyService;
import com.wiproevents.utils.Helper;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;
import java.util.List;

/**
 * The poll REST controller. Is effectively thread safe.
 *
 * @author TCSDEVELOPER
 * @version 1.0
 */
@RestController
@RequestMapping("/events")
@NoArgsConstructor
public class EventEngagementController {

    @Autowired
    private PollService pollService;

    @Autowired
    private QuizService quizService;

    @Autowired
    private SurveyService surveyService;

    @PostConstruct
    public void postConstruct() {
        Helper.checkConfigNotNull(pollService, "pollService");
        Helper.checkConfigNotNull(quizService, "quizService");
        Helper.checkConfigNotNull(surveyService, "surveyService");
    }


    /**
     * This method is used to retrieve an entity.
     *
     * @param eventId the id of the entity to retrieve
     * @return the match entity
     * @throws IllegalArgumentException if id is not positive
     * @throws EntityNotFoundException if the entity does not exist
     * @throws AttendeeException if any other error occurred during operation
     */
    @RequestMapping(value = "{eventId}/engagements", method = RequestMethod.GET)
    public Engagement get(@PathVariable String eventId) throws AttendeeException {
        // search polls
        PollSearchCriteria pollCriteria = new PollSearchCriteria();
        pollCriteria.setEventId(eventId);
        List<Poll> polls = pollService.search(pollCriteria, null).getEntities();

        // search surveys
        SurveySearchCriteria surveyCriteria = new SurveySearchCriteria();
        surveyCriteria.setEventId(eventId);
        List<Survey> surveys = surveyService.search(surveyCriteria, null).getEntities();


        // search quizes
        QuizSearchCriteria quizCriteria = new QuizSearchCriteria();
        quizCriteria.setEventId(eventId);
        List<Quiz> quizes = quizService.search(quizCriteria, null).getEntities();

        Engagement engagement = new Engagement();
        engagement.setPolls(polls);
        engagement.setQuizes(quizes);
        engagement.setSurveys(surveys);

        return engagement;
    }
}

