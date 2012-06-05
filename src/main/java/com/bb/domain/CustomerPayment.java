package com.bb.domain;

import com.bb.domain.ref.RefPaymentType;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;
import java.util.List;

@Configurable
@Entity
public class CustomerPayment {

    @NotNull
    @ManyToOne
    private Customer customer;

    @NotNull
    @ManyToOne
    private RefPaymentType paymentType;

    @NotNull
    @Size(min = 2, max = 30)
    private String accountId;

    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(style = "M-")
    private Date startDate;

    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(style = "M-")
    private Date endDate;

    @Id
    @SequenceGenerator(name = "customerPaymentGen", sequenceName = "CUSTOMER_PAYMENT_SEQ")
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "customerPaymentGen")
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


    public Customer getCustomer() {
        return this.customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public RefPaymentType getPaymentType() {
        return this.paymentType;
    }

    public void setPaymentType(RefPaymentType paymentType) {
        this.paymentType = paymentType;
    }

    public String getAccountId() {
        return this.accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
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


    @PersistenceContext
    transient EntityManager entityManager;

    public static final EntityManager entityManager() {
        EntityManager em = new CustomerPayment().entityManager;
        if (em == null) throw new IllegalStateException("Entity manager has not been injected (is the Spring Aspects JAR configured as an AJC/AJDT aspects library?)");
        return em;
    }

    public static long countCustomerPayments() {
        return entityManager().createQuery("SELECT COUNT(o) FROM CustomerPayment o", Long.class).getSingleResult();
    }

    public static List<CustomerPayment> findAllCustomerPayments() {
        return entityManager().createQuery("SELECT o FROM CustomerPayment o", CustomerPayment.class).getResultList();
    }

    public static CustomerPayment findCustomerPayment(Long id) {
        if (id == null) return null;
        return entityManager().find(CustomerPayment.class, id);
    }

    public static List<CustomerPayment> findCustomerPaymentEntries(int firstResult, int maxResults) {
        return entityManager().createQuery("SELECT o FROM CustomerPayment o", CustomerPayment.class).setFirstResult(firstResult).setMaxResults(maxResults).getResultList();
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
            CustomerPayment attached = findCustomerPayment(this.id);
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
    public CustomerPayment merge() {
        if (this.entityManager == null) this.entityManager = entityManager();
        CustomerPayment merged = this.entityManager.merge(this);
        this.entityManager.flush();
        return merged;
    }
}
