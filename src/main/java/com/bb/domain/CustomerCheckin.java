package com.bb.domain;

import com.bb.reference.CustomerCheckinEndType;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.*;

@Configurable
@Entity
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

    @Transient
    private long timeLengthInMinute;

    public static TypedQuery<CustomerCheckin> findCustomerCheckinsByCustomerAndDate(Long customerId, Date start, Date end) {
        if (customerId == null) throw new IllegalArgumentException("The customerId argument is required");
        EntityManager em = entityManager();
        TypedQuery<CustomerCheckin> q = em.createQuery("SELECT o FROM CustomerCheckin AS o WHERE o.customer.id = :customerId and o.isApproved=true", CustomerCheckin.class);
        q.setParameter("customerId", customerId);
        return q;
    }

    public static TypedQuery<CustomerCheckin> findCustomerCheckinsByCustomerAndApproved(Long customerId) {
        if (customerId == null) throw new IllegalArgumentException("The customerId argument is required");
        EntityManager em = entityManager();
        TypedQuery<CustomerCheckin> q = em.createQuery("SELECT o FROM CustomerCheckin AS o WHERE o.customer.id = :customerId and o.isApproved=true", CustomerCheckin.class);
        q.setParameter("customerId", customerId);
        return q;
    }

    public static TypedQuery<CustomerCheckin> findCustomerCheckinsByCustomer(Long customerId) {
        if (customerId == null) throw new IllegalArgumentException("The customerId argument is required");
        EntityManager em = entityManager();
        TypedQuery<CustomerCheckin> q = em.createQuery("SELECT o FROM CustomerCheckin AS o WHERE o.customer.id = :customerId" +
                " order by o.startDate desc", CustomerCheckin.class);
        q.setParameter("customerId", customerId);
        return q;
    }


    public static Integer getCompletionRateByCustomer(Long customerId) {
        int checkin = findCustomerCheckinsByCustomerAndApproved(customerId).getResultList().size();
        int commits = CustomerProduct.getAllCommitsByCustomerId(customerId);
//        System.out.println(checkinStart+" "+commits);
//        System.out.println("xxx"+ (int)( ((float)checkinStart / (float)commits)*100));
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

    public long getTimeLengthInMinute() {
        if (startDate == null || endDate == null || endDate.before(startDate)) {
            return -1;
        }
        return (endDate.getTime()-startDate.getTime())/1000/60;
    }

    public Customer getCustomer() {
        return this.customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public Location getLocation() {
        return this.location;
    }

    public void setLocation(Location location) {
        this.location = location;
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

    public CustomerCheckinEndType getEndType() {
        return this.endType;
    }

    public void setEndType(CustomerCheckinEndType endType) {
        this.endType = endType;
    }

    public Boolean getIsApproved() {
        return this.isApproved;
    }

    public void setIsApproved(Boolean isApproved) {
        this.isApproved = isApproved;
    }

    public void setTimeLengthInMinute(long timeLengthInMinute) {
        this.timeLengthInMinute = timeLengthInMinute;
    }

    @PersistenceContext
    transient EntityManager entityManager;

    public static final EntityManager entityManager() {
        EntityManager em = new CustomerCheckin().entityManager;
        if (em == null) throw new IllegalStateException("Entity manager has not been injected (is the Spring Aspects JAR configured as an AJC/AJDT aspects library?)");
        return em;
    }

    public static long countCustomerCheckins() {
        return entityManager().createQuery("SELECT COUNT(o) FROM CustomerCheckin o", Long.class).getSingleResult();
    }

    public static List<CustomerCheckin> findAllCustomerCheckins() {
        return entityManager().createQuery("SELECT o FROM CustomerCheckin o", CustomerCheckin.class).getResultList();
    }

    public static CustomerCheckin findCustomerCheckin(Long id) {
        if (id == null) return null;
        return entityManager().find(CustomerCheckin.class, id);
    }

    public static List<CustomerCheckin> findCustomerCheckinEntries(int firstResult, int maxResults) {
        return entityManager().createQuery("SELECT o FROM CustomerCheckin o", CustomerCheckin.class).setFirstResult(firstResult).setMaxResults(maxResults).getResultList();
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
            CustomerCheckin attached = findCustomerCheckin(this.id);
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
    public CustomerCheckin merge() {
        if (this.entityManager == null) this.entityManager = entityManager();
        CustomerCheckin merged = this.entityManager.merge(this);
        this.entityManager.flush();
        return merged;
    }

    @Id
    @SequenceGenerator(name = "customerCheckinGen", sequenceName = "CUSTOMER_CHECKIN_SEQ")
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "customerCheckinGen")
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
