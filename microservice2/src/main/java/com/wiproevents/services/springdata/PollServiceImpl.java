/*
 * Copyright (c) 2018 TopCoder, Inc. All rights reserved.
 */
package com.wiproevents.services.springdata;

import com.wiproevents.entities.Poll;
import com.wiproevents.entities.PollTotalResult;
import com.wiproevents.entities.answers.UserPollAnswer;
import com.wiproevents.entities.answers.UserPollQuestionAnswer;
import com.wiproevents.entities.criteria.PollSearchCriteria;
import com.wiproevents.entities.criteria.UserPollAnswerSearchCriteria;
import com.wiproevents.exceptions.AttendeeException;
import com.wiproevents.services.PollService;
import com.wiproevents.services.UserPollAnswerService;
import com.wiproevents.utils.springdata.extensions.DocumentDbSpecification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * The Spring Data JPA implementation of PollService,
 * extends BaseService<Poll, PollSearchCriteria>. Effectively thread safe after configuration.
 */
@Service
public class PollServiceImpl
        extends BaseService<Poll, PollSearchCriteria> implements PollService {

    @Autowired
    private UserPollAnswerService userPollAnswerService;

    @Override
    public PollTotalResult calculatePollTotalResult(String pollId) throws AttendeeException {
        UserPollAnswerSearchCriteria criteria = new UserPollAnswerSearchCriteria();
        criteria.setPollId(pollId);

        PollTotalResult result = new PollTotalResult();
        List<UserPollAnswer> answers = userPollAnswerService.search(criteria, null).getEntities();
        // aggregate by the questions
        for (UserPollAnswer answer: answers) {
            for (UserPollQuestionAnswer questionAnswer: answer.getUserAnswers()) {
                result.getOptionResults().add(questionAnswer);
            }
        }

        result.setPoll(get(pollId));

        return result;
    }

    /**
     * This method is used to get the specification.
     *
     * @param criteria the search criteria
     * @return the specification
     * @throws AttendeeException if any other error occurred during operation
     */
    @Override
    protected DocumentDbSpecification<Poll> getSpecification(PollSearchCriteria criteria)
            throws AttendeeException {
        return new PollSpecification(criteria);
    }
}

