package com.bb.domain;

import com.bb.reference.CustomerCheckinEndType;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.jpa.activerecord.RooJpaActiveRecord;
import org.springframework.roo.addon.tostring.RooToString;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.*;

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

    public static TypedQuery<CustomerCheckin> findCustomerCheckinsByCustomerAndDate(Long customerId, Date start, Date end) {
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

    public static Integer getCompletionRateByCustomer(Long customerId) {
        int checkin = findCustomerCheckinsByCustomerAndApproved(customerId).getResultList().size();
        int commits = CustomerProduct.getAllCommitsByCustomerId(customerId);
//        System.out.println(checkin+" "+commits);
//        System.out.println("xxx"+ (int)( ((float)checkin / (float)commits)*100));
        return (int) (((float) checkin / (float) commits) * 100);
    }

    public static Integer getCompletionRateRankByCustomer(Long customerId) {

        Map<Long, Integer> map = new HashMap<Long, Integer>();
        int rank = 0;
        List<Customer> customers = Customer.findAllCustomers();
        System.out.println("customer size="+customers.size());
        for (Customer customer : customers) {
            map.put(customer.getId(), getCompletionRateByCustomer(customer.getId()));
        }

        System.out.println(map);
        List<Integer> sortedRates = new ArrayList<Integer>();
        sortedRates.addAll(map.values());
        Collections.sort(sortedRates);
        Integer theRate = map.get(customerId);
        rank = sortedRates.size() - sortedRates.indexOf(theRate);

        return rank;

    }

}
