package com.wiproevents.entities;

import com.microsoft.azure.spring.data.documentdb.core.mapping.Document;
import com.wiproevents.entities.statuses.EmailStatus;
import com.wiproevents.utils.springdata.extensions.Reference;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.NotBlank;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by wangjinggang on 2018/2/16.
 */
@Document(collection = "email")
@Getter
@Setter
public class Email extends AuditableUserEntity {
    private List<String> emails = new ArrayList<>();

    @NotBlank
    private String title;

    @NotBlank
    private String text;

    private boolean scheduled;

    private Date dateScheduled;

    @Reference
    private Timezone timezone;

    private EmailStatus status;
}
