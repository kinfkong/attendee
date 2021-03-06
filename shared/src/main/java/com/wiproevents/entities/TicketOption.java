package com.wiproevents.entities;

import lombok.Getter;
import lombok.Setter;

/**
 * Created by wangjinggang on 2018/2/7.
 */
@Getter
@Setter
public class TicketOption implements Model  {
    private String name;
    private Double price;
    private Double quota;
    private String description;
}
