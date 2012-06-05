package com.bb.domain;

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

@Entity
@Configurable
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


    public Customer getCustomer() {
        return this.customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public Card getCard() {
        return this.card;
    }

    public void setCard(Card card) {
        this.card = card;
    }

    public Date getUsedDate() {
        return this.usedDate;
    }

    public void setUsedDate(Date usedDate) {
        this.usedDate = usedDate;
    }

    public Date getIssuedDate() {
        return this.issuedDate;
    }

    public void setIssuedDate(Date issuedDate) {
        this.issuedDate = issuedDate;
    }

    public String getStatus() {
        return this.status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public boolean isIsWizard() {
        return this.isWizard;
    }

    public void setIsWizard(boolean isWizard) {
        this.isWizard = isWizard;
    }

    @PersistenceContext
    transient EntityManager entityManager;

    public static final EntityManager entityManager() {
        EntityManager em = new CustomerCard().entityManager;
        if (em == null) throw new IllegalStateException("Entity manager has not been injected (is the Spring Aspects JAR configured as an AJC/AJDT aspects library?)");
        return em;
    }

    public static long countCustomerCards() {
        return entityManager().createQuery("SELECT COUNT(o) FROM CustomerCard o", Long.class).getSingleResult();
    }

    public static List<CustomerCard> findAllCustomerCards() {
        return entityManager().createQuery("SELECT o FROM CustomerCard o", CustomerCard.class).getResultList();
    }

    public static CustomerCard findCustomerCard(Long id) {
        if (id == null) return null;
        return entityManager().find(CustomerCard.class, id);
    }

    public static List<CustomerCard> findCustomerCardEntries(int firstResult, int maxResults) {
        return entityManager().createQuery("SELECT o FROM CustomerCard o", CustomerCard.class).setFirstResult(firstResult).setMaxResults(maxResults).getResultList();
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
            CustomerCard attached = findCustomerCard(this.id);
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
    public CustomerCard merge() {
        if (this.entityManager == null) this.entityManager = entityManager();
        CustomerCard merged = this.entityManager.merge(this);
        this.entityManager.flush();
        return merged;
    }

    @Id
    @SequenceGenerator(name = "customerCardGen", sequenceName = "CUSTOMER_CARD_SEQ")
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "customerCardGen")
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

