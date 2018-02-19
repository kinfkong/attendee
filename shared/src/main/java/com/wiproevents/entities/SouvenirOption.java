package com.wiproevents.entities;

import com.wiproevents.utils.springdata.extensions.Embed;
import lombok.Getter;
import lombok.Setter;

/**
 * Created by wangjinggang on 2018/2/19.
 */
@Getter
@Setter
public class SouvenirOption implements Model {
    private String name;
    private String type;
    @Embed
    private Souvenir souvenir;
}
