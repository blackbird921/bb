package com.bb.domain;

import java.util.Date;
import java.util.List;
import java.util.Map;
import javax.persistence.*;
import javax.validation.constraints.NotNull;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.jpa.activerecord.RooJpaActiveRecord;
import org.springframework.roo.addon.tostring.RooToString;
import org.springframework.transaction.annotation.Transactional;

@Configurable
@Entity
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
        EntityManager em = entityManager();
        Query q = em.createQuery("SELECT o FROM CustomerProfit AS o WHERE o.customer.id = :id", CustomerProfit.class);
        q.setParameter("id", id);
        return q.getResultList();
    }

    public static Integer countProfitByCustomerId(Long id) {
        EntityManager em = entityManager();
        TypedQuery<Integer> q = em.createQuery("SELECT sum(o.amount) FROM CustomerProfit AS o "
                + "WHERE o.customer.id = :id", Integer.class);
        q.setParameter("id", id);
        Integer result = q.getSingleResult();
        if (result != null) {
            return result;
        }else{
            return 0;
        }
    }


    public static Integer getProfitRankByCustomerId(Long cid) {
        EntityManager em = entityManager();
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


    public Customer getCustomer() {
        return this.customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public Date getStartDate() {
        return this.startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return this.endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public Long getAmount() {
        return this.amount;
    }

    public void setAmount(Long amount) {
        this.amount = amount;
    }

    @PersistenceContext
    transient EntityManager entityManager;

    public static final EntityManager entityManager() {
        EntityManager em = new CustomerProfit().entityManager;
        if (em == null) throw new IllegalStateException("Entity manager has not been injected (is the Spring Aspects JAR configured as an AJC/AJDT aspects library?)");
        return em;
    }

    public static long countCustomerProfits() {
        return entityManager().createQuery("SELECT COUNT(o) FROM CustomerProfit o", Long.class).getSingleResult();
    }

    public static List<CustomerProfit> findAllCustomerProfits() {
        return entityManager().createQuery("SELECT o FROM CustomerProfit o", CustomerProfit.class).getResultList();
    }

    public static CustomerProfit findCustomerProfit(Long id) {
        if (id == null) return null;
        return entityManager().find(CustomerProfit.class, id);
    }

    public static List<CustomerProfit> findCustomerProfitEntries(int firstResult, int maxResults) {
        return entityManager().createQuery("SELECT o FROM CustomerProfit o", CustomerProfit.class).setFirstResult(firstResult).setMaxResults(maxResults).getResultList();
    }

    @Transactional
    public void persist() {
        if (this.entityManager == null) this.entityManager = entityManager();
        this.entityManager.persist(this);
    }

    @Transactional
    public void remove() {
        if (this.entityManager == null) this.entityManager = entityManager();
        if (this.entityManager.contains(this)) {
            this.entityManager.remove(this);
        } else {
            CustomerProfit attached = findCustomerProfit(this.id);
            this.entityManager.remove(attached);
        }
    }

    @Transactional
    public void flush() {
        if (this.entityManager == null) this.entityManager = entityManager();
        this.entityManager.flush();
    }

    @Transactional
    public void clear() {
        if (this.entityManager == null) this.entityManager = entityManager();
        this.entityManager.clear();
    }

    @Transactional
    public CustomerProfit merge() {
        if (this.entityManager == null) this.entityManager = entityManager();
        CustomerProfit merged = this.entityManager.merge(this);
        this.entityManager.flush();
        return merged;

    }

    @Id
    @SequenceGenerator(name = "customerProfitGen", sequenceName = "CUSTOMER_PROFIT_SEQ")
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "customerProfitGen")
    @Column(name = "id")
    private Long id;

    @Version
    @Column(name = "version")
    private Integer version;

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getVersion() {
        return this.version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    public String toString() {
        return ReflectionToStringBuilder.toString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }

}
