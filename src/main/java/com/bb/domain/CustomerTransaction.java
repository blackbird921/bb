package com.bb.domain;

import com.bb.domain.ref.RefPaymentTxType;
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
@RooJpaActiveRecord(sequenceName = "CUSTOMER_TRANSACTION_SEQ")
public class CustomerTransaction {

    @NotNull
    @ManyToOne
    private Customer customer;

    @NotNull
    @ManyToOne
    private CustomerPayment customerPayment;

    private Long amount;

    @NotNull
    @ManyToOne
    private RefPaymentTxType transactionType;

    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(style = "M-")
    private Date transactionDate;

    private String transactionError;
}