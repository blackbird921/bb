package com.bb.domain;

import com.bb.reference.LocationStatus;
import java.util.Date;
import javax.persistence.Enumerated;
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
@RooJpaActiveRecord(sequenceName = "LOCATION_SEQ")
public class Location {

    @NotNull
    @Size(min = 2, max = 30)
    private String name;

    @NotNull
    @Size(max = 100)
    private String address;

    @NotNull
    @Size(max = 30)
    private String city;

    private Float latitude;

    private Float longitude;

    @Size(max = 30)
    private String phone;

    @Size(max = 30)
    private String contactName;

    @NotNull
    @Enumerated
    private LocationStatus status;

    @Size(max = 200)
    private String rejectionReason;

    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(style = "M-")
    private Date create_date;

    @ManyToOne
    private Customer customer;
}
