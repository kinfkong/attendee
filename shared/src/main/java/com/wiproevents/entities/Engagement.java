package com.wiproevents.entities;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wangjinggang on 2018/2/19.
 */
@Getter
@Setter
public class Engagement {
    private List<Poll> polls = new ArrayList<>();
    private List<Survey> surveys = new ArrayList<>();
    private List<Quiz> quizes = new ArrayList<>();
}

