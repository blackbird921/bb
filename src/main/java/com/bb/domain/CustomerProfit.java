package com.bb.domain;

import java.util.Date;
import java.util.List;
import java.util.Map;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.jpa.activerecord.RooJpaActiveRecord;
import org.springframework.roo.addon.tostring.RooToString;

@RooJavaBean
@RooToString
@RooJpaActiveRecord(sequenceName = "CUSTOMER_PROFIT_SEQ")
public class CustomerProfit {

    @NotNull
    @ManyToOne
    private Customer customer;

    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(style = "M-")
    private Date startDate;

    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(style = "M-")
    private Date endDate;

    @NotNull
    private Long amount;

    public static List<CustomerProfit> findAllProfitByCustomerId(Long id) {
        EntityManager em = CustomerProfit.entityManager();
        Query q = em.createQuery("SELECT o FROM CustomerProfit AS o WHERE o.customer.id = :id", CustomerProfit.class);
        q.setParameter("id", id);
        return q.getResultList();
    }

    public static TypedQuery<Long> countProfitByCustomerId(Long id) {
        EntityManager em = CustomerProfit.entityManager();
        TypedQuery<Long> q = em.createQuery("SELECT sum(o.amount) FROM CustomerProfit AS o "
                + "WHERE o.customer.id = :id", Long.class);
        q.setParameter("id", id);
        return q;
    }


    public static Integer getProfitRankByCustomerId(Long cid) {
        EntityManager em = CustomerProfit.entityManager();
        TypedQuery<Long> q = em.createQuery("SELECT o.customer.id FROM CustomerProfit AS o "
                + "group by o.customer.id order by sum(o.amount) desc", Long.class);
        List<Long> list = q.getResultList();
        for (Long item : list) {
            if (item.equals(cid)) {
                return list.indexOf(item)+1;
            }
        }

        return 0;
    }

}
