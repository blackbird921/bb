package com.bb.domain;

import com.bb.reference.CustomerCheckinEndType;
import java.util.Date;
import javax.persistence.Enumerated;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.jpa.activerecord.RooJpaActiveRecord;
import org.springframework.roo.addon.tostring.RooToString;

@RooJavaBean
@RooToString
@RooJpaActiveRecord(sequenceName = "CUSTOMER_CHECKIN_SEQ")
public class CustomerCheckin {

    @NotNull
    @ManyToOne
    private Customer customer;

    @NotNull
    @ManyToOne
    private Location location;

    @NotNull
    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(style = "M-")
    private Date startDate;

    @NotNull
    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(style = "M-")
    private Date endDate;

    @Enumerated
    private CustomerCheckinEndType endType;

    private Boolean isApproved;
}
