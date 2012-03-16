package com.bb.domain;

import com.bb.reference.CustomerCheckinEndType;
import java.util.Date;
import javax.persistence.*;
import javax.validation.constraints.NotNull;

import com.bb.reference.CustomerRole;
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

    public static TypedQuery<CustomerCheckin> findCustomerCheckinsByCustomerAndDate(Long customerId, Date start, Date end ) {
        if (customerId == null) throw new IllegalArgumentException("The customerId argument is required");
        EntityManager em = CustomerCheckin.entityManager();
        TypedQuery<CustomerCheckin> q = em.createQuery("SELECT o FROM CustomerCheckin AS o WHERE o.customer.id = :customerId and o.isApproved=true", CustomerCheckin.class);
        q.setParameter("customerId", customerId);
        return q;
    }

    public static TypedQuery<CustomerCheckin> findCustomerCheckinsByCustomerAndApproved(Long customerId) {
        if (customerId == null) throw new IllegalArgumentException("The customerId argument is required");
        EntityManager em = CustomerCheckin.entityManager();
        TypedQuery<CustomerCheckin> q = em.createQuery("SELECT o FROM CustomerCheckin AS o WHERE o.customer.id = :customerId and o.isApproved=true", CustomerCheckin.class);
        q.setParameter("customerId", customerId);
        return q;
    }


}
