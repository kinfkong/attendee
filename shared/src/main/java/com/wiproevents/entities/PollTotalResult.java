package com.wiproevents.entities;


import com.wiproevents.entities.answers.UserPollQuestionAnswer;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class PollTotalResult {
    private Poll poll;
    private List<UserPollQuestionAnswer> optionResults = new ArrayList<>();
}
