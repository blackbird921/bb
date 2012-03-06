package com.bb.domain;

import com.bb.domain.ref.RefPaymentType;
import java.util.Date;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.jpa.activerecord.RooJpaActiveRecord;
import org.springframework.roo.addon.tostring.RooToString;

@RooJavaBean
@RooToString
@RooJpaActiveRecord(sequenceName = "CUSTOMER_PAYMENT_SEQ")
public class CustomerPayment {

    @NotNull
    @ManyToOne
    private Customer customer;

    @NotNull
    @ManyToOne
    private RefPaymentType paymentType;

    @NotNull
    @Size(min = 2, max = 30)
    private String accountId;

    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(style = "M-")
    private Date startDate;

    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(style = "M-")
    private Date endDate;
}
