package com.bb.domain;

import java.util.Date;
import java.util.List;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.jpa.activerecord.RooJpaActiveRecord;
import org.springframework.roo.addon.tostring.RooToString;

@RooJavaBean
@RooToString
@RooJpaActiveRecord(sequenceName = "CUSTOMER_CARD_SEQ")
public class CustomerCard {

    @NotNull
    @ManyToOne
    private Customer customer;

    @NotNull
    @ManyToOne
    private Card card;

    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(style = "M-")
    private Date usedDate;

    @NotNull
    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(style = "M-")
    private Date issuedDate;

    @NotNull
    @Size(max = 50)
    private String status;

    @Transient
    private boolean isWizard;

    public boolean getWizard() {
        return isWizard;
    }

    public void setWizard(boolean wizard) {
        isWizard = wizard;
    }

    public static List<CustomerCard> findAllByCustomerId(Long cid) {
        TypedQuery<CustomerCard> query = entityManager().createQuery("SELECT o FROM CustomerCard o WHERE o.customer.id=:cid", CustomerCard.class);
        query.setParameter("cid", cid);
        return query.getResultList();
    }
}

