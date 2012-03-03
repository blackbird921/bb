package com.bb.domain;

import javax.persistence.ManyToOne;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.jpa.activerecord.RooJpaActiveRecord;
import org.springframework.roo.addon.tostring.RooToString;

@RooJavaBean
@RooToString
@RooJpaActiveRecord(sequenceName = "CUSTOMER_PRODUCT_SEQ")
public class CustomerProduct {

    @NotNull
    @ManyToOne
    private Customer customer;

    @NotNull
    @ManyToOne
    private Product product;

    @NotNull
    @Min(201201L)
    private Long startWeek;

    @Min(201201L)
    private Long endWeek;
}
