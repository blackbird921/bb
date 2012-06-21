package com.bb.domain;

import com.bb.reference.LocationStatus;
import com.bb.util.GpsDistanceCalc;
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
public class Location {

    @NotNull
    @Size(min = 2, max = 30)
    private String name;

    @NotNull
    @Size(max = 100)
    private String address;

    @Size(max = 30)
    private String city;

    private Double latitude;

    private Double longitude;

    @Size(max = 30)
    private String phone;

    @Size(max = 30)
    private String contactName;

    @NotNull
    @Enumerated
    private LocationStatus status;

    @Size(max = 200)
    private String rejectionReason;

    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(style = "M-")
    private Date create_date;

    @ManyToOne
    private Customer customer;

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return this.address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCity() {
        return this.city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public Double getLatitude() {
        return this.latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return this.longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public String getPhone() {
        return this.phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getContactName() {
        return this.contactName;
    }

    public void setContactName(String contactName) {
        this.contactName = contactName;
    }

    public LocationStatus getStatus() {
        return this.status;
    }

    public void setStatus(LocationStatus status) {
        this.status = status;
    }

    public String getRejectionReason() {
        return this.rejectionReason;
    }

    public void setRejectionReason(String rejectionReason) {
        this.rejectionReason = rejectionReason;
    }

    public Date getCreate_date() {
        return this.create_date;
    }

    public void setCreate_date(Date create_date) {
        this.create_date = create_date;
    }

    public Customer getCustomer() {
        return this.customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    @PersistenceContext
    transient EntityManager entityManager;

    public static final EntityManager entityManager() {
        EntityManager em = new Location().entityManager;
        if (em == null) throw new IllegalStateException("Entity manager has not been injected (is the Spring Aspects JAR configured as an AJC/AJDT aspects library?)");
        return em;
    }

    public static long countLocations() {
        return entityManager().createQuery("SELECT COUNT(o) FROM Location o", Long.class).getSingleResult();
    }

    public static List<Location> findAllLocations() {
        return entityManager().createQuery("SELECT o FROM Location o", Location.class).getResultList();
    }

    public static List<Location> findAllLocationsInRange(GpsDistanceCalc.GpsRange range) {

        TypedQuery<Location> q = entityManager().createQuery("SELECT o FROM Location AS o " +
                "WHERE o.latitude > :minLat AND o.longitude > :minLon " +
                "AND o.latitude < :maxLat AND o.longitude < :maxLon ", Location.class);
        q.setParameter("minLat", (double)range.minLat);
        q.setParameter("minLon", (double)range.minLon);
        q.setParameter("maxLat", (double)range.maxLat);
        q.setParameter("maxLon", (double)range.maxLon);

        return q.getResultList();
    }

    public static Location findLocation(Long id) {
        if (id == null) return null;
        return entityManager().find(Location.class, id);
    }

    public static List<Location> findLocationEntries(int firstResult, int maxResults) {
        return entityManager().createQuery("SELECT o FROM Location o", Location.class).setFirstResult(firstResult).setMaxResults(maxResults).getResultList();
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
            Location attached = findLocation(this.id);
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
    public Location merge() {
        if (this.entityManager == null) this.entityManager = entityManager();
        Location merged = this.entityManager.merge(this);
        this.entityManager.flush();
        return merged;
    }


    @Id
    @SequenceGenerator(name = "locationGen", sequenceName = "LOCATION_SEQ")
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "locationGen")
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
