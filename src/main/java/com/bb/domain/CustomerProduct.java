package com.bb.domain;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import org.springframework.format.annotation.DateTimeFormat;
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
    private ProductCommit productCommit;

    @NotNull
    @ManyToOne
    private ProductStake productStake;

    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(style = "M-")
    private Date startDate;

    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(style = "M-")
    private Date endDate;

    @Transient
    private boolean isShowStartDate = true;

    public boolean isShowStartDate() {
        return isShowStartDate;
    }

    public void setShowStartDate(boolean showStartDate) {
        isShowStartDate = showStartDate;
    }

    public static List<CustomerProduct> findAllByCustomerId(Long cid) {
        TypedQuery<CustomerProduct> query = entityManager().createQuery("SELECT o FROM CustomerProduct o WHERE o.customer.id=:cid", CustomerProduct.class);
        query.setParameter("cid", cid);
        return query.getResultList();
    }

    public static Integer getAllCommitsByCustomerId(Long cid) {
        List<CustomerProduct> allProducts = findAllByCustomerId(cid);
        int count = 0;
        Date today = Calendar.getInstance().getTime();
        for (CustomerProduct cp : allProducts) {
            if (cp.endDate == null) {
                cp.endDate=today;
            }
            long weekCount = (cp.endDate.getTime() - cp.startDate.getTime()) / 1000 / 60 / 60 / 24 / 7;
//            System.out.println("weekcount="+weekCount);
//            System.out.println(cp.getProductCommit().getCommits());
            count += cp.getProductCommit().getCommits() * weekCount;
        }
        return count;

    }

}
