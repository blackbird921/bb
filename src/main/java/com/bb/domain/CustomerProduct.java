package com.bb.domain;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
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

    public Customer getCustomer() {
        return this.customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public ProductCommit getProductCommit() {
        return this.productCommit;
    }

    public void setProductCommit(ProductCommit productCommit) {
        this.productCommit = productCommit;
    }

    public ProductStake getProductStake() {
        return this.productStake;
    }

    public void setProductStake(ProductStake productStake) {
        this.productStake = productStake;
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

    public boolean isIsShowStartDate() {
        return this.isShowStartDate;
    }

    public void setIsShowStartDate(boolean isShowStartDate) {
        this.isShowStartDate = isShowStartDate;
    }

    @PersistenceContext
    transient EntityManager entityManager;

    public static final EntityManager entityManager() {
        EntityManager em = new CustomerProduct().entityManager;
        if (em == null) throw new IllegalStateException("Entity manager has not been injected (is the Spring Aspects JAR configured as an AJC/AJDT aspects library?)");
        return em;
    }

    public static long countCustomerProducts() {
        return entityManager().createQuery("SELECT COUNT(o) FROM CustomerProduct o", Long.class).getSingleResult();
    }

    public static List<CustomerProduct> findAllCustomerProducts() {
        return entityManager().createQuery("SELECT o FROM CustomerProduct o", CustomerProduct.class).getResultList();
    }

    public static CustomerProduct findCustomerProduct(Long id) {
        if (id == null) return null;
        return entityManager().find(CustomerProduct.class, id);
    }

    public static List<CustomerProduct> findCustomerProductEntries(int firstResult, int maxResults) {
        return entityManager().createQuery("SELECT o FROM CustomerProduct o", CustomerProduct.class).setFirstResult(firstResult).setMaxResults(maxResults).getResultList();
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
            CustomerProduct attached = findCustomerProduct(this.id);
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
    public CustomerProduct merge() {
        if (this.entityManager == null) this.entityManager = entityManager();
        CustomerProduct merged = this.entityManager.merge(this);
        this.entityManager.flush();
        return merged;
    }

    @Id
    @SequenceGenerator(name = "customerProductGen", sequenceName = "CUSTOMER_PRODUCT_SEQ")
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "customerProductGen")
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
