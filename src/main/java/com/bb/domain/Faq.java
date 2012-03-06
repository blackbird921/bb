package com.bb.domain;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.jpa.activerecord.RooJpaActiveRecord;
import org.springframework.roo.addon.tostring.RooToString;

@RooJavaBean
@RooToString
@RooJpaActiveRecord(sequenceName = "FAQ_SEQ")
public class Faq {

    @NotNull
    @Size(min = 2, max = 100)
    private String question;

    @NotNull
    @Size(min = 2, max = 500)
    private String answer;

    @NotNull
    private Long questionOrder;
}
