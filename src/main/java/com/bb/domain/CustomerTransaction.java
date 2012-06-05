package com.bb.domain;

import com.bb.domain.ref.RefPaymentTxType;
import java.util.Date;
import java.util.List;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

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
public class CustomerTransaction {

    @NotNull
    @ManyToOne
    private Customer customer;

    @NotNull
    @ManyToOne
    private CustomerPayment customerPayment;

    private Long amount;

    @NotNull
    @ManyToOne
    private RefPaymentTxType transactionType;

    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(style = "M-")
    private Date transactionDate;

    private String transactionError;

    public static List<CustomerTransaction> findAllTransactionByCustomerId(Long id) {
        EntityManager em = entityManager();
        Query q = em.createQuery("SELECT o FROM CustomerTransaction AS o WHERE o.customer.id = :id", CustomerTransaction.class);
        q.setParameter("id", id);
        return q.getResultList();
    }
    public Customer getCustomer() {
        return this.customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public CustomerPayment getCustomerPayment() {
        return this.customerPayment;
    }

    public void setCustomerPayment(CustomerPayment customerPayment) {
        this.customerPayment = customerPayment;
    }

    public Long getAmount() {
        return this.amount;
    }

    public void setAmount(Long amount) {
        this.amount = amount;
    }

    public RefPaymentTxType getTransactionType() {
        return this.transactionType;
    }

    public void setTransactionType(RefPaymentTxType transactionType) {
        this.transactionType = transactionType;
    }

    public Date getTransactionDate() {
        return this.transactionDate;
    }

    public void setTransactionDate(Date transactionDate) {
        this.transactionDate = transactionDate;
    }

    public String getTransactionError() {
        return this.transactionError;
    }

    public void setTransactionError(String transactionError) {
        this.transactionError = transactionError;
    }

    @PersistenceContext
    transient EntityManager entityManager;

    public static final EntityManager entityManager() {
        EntityManager em = new CustomerTransaction().entityManager;
        if (em == null) throw new IllegalStateException("Entity manager has not been injected (is the Spring Aspects JAR configured as an AJC/AJDT aspects library?)");
        return em;
    }

    public static long countCustomerTransactions() {
        return entityManager().createQuery("SELECT COUNT(o) FROM CustomerTransaction o", Long.class).getSingleResult();
    }

    public static List<CustomerTransaction> findAllCustomerTransactions() {
        return entityManager().createQuery("SELECT o FROM CustomerTransaction o", CustomerTransaction.class).getResultList();
    }

    public static CustomerTransaction findCustomerTransaction(Long id) {
        if (id == null) return null;
        return entityManager().find(CustomerTransaction.class, id);
    }

    public static List<CustomerTransaction> findCustomerTransactionEntries(int firstResult, int maxResults) {
        return entityManager().createQuery("SELECT o FROM CustomerTransaction o", CustomerTransaction.class).setFirstResult(firstResult).setMaxResults(maxResults).getResultList();
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
            CustomerTransaction attached = findCustomerTransaction(this.id);
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
    public CustomerTransaction merge() {
        if (this.entityManager == null) this.entityManager = entityManager();
        CustomerTransaction merged = this.entityManager.merge(this);
        this.entityManager.flush();
        return merged;
    }


    @Id
    @SequenceGenerator(name = "customerTransactionGen", sequenceName = "CUSTOMER_TRANSACTION_SEQ")
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "customerTransactionGen")
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
