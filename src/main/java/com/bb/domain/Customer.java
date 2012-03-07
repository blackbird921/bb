package com.bb.domain;

import com.bb.domain.ref.RefSex;
import com.bb.reference.CustomerRole;
import com.bb.reference.CustomerStatus;
import java.util.Date;
import javax.persistence.Enumerated;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.Size;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.jpa.activerecord.RooJpaActiveRecord;
import org.springframework.roo.addon.tostring.RooToString;

@RooJavaBean
@RooToString
@RooJpaActiveRecord(sequenceName = "CUSTOMER_SEQ")
public class Customer {

    @NotNull
    @Size(min = 1, max = 30)
    private String username;

    @NotNull
    @Size(min = 4, max = 15)
    private String password;

    @NotNull
    @Size(min = 5, max = 30)
    private String email;

    @NotNull
    @Enumerated
    private CustomerStatus status;

    @Enumerated
    private CustomerRole customerRole;

    @Size(max = 30)
    private String name;

    @Size(max = 100)
    private String address;

    @Size(max = 30)
    private String city;

    @Size(max = 30)
    private String phone;

    @ManyToOne
    private RefSex sex;

    @Past
    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(style = "M-")
    private Date birthday;

    @Size(max = 500)
    private String bio;

    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(style = "M-")
    private Date registrationDate;

    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(style = "M-")
    private Date disableStartDate;

    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(style = "M-")
    private Date disableEndDate;

    @Size(max = 100)
    private String disableReason;
}
