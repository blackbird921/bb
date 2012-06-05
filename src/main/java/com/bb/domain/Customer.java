package com.bb.domain;

import com.bb.domain.ref.RefSex;
import com.bb.reference.CustomerRole;
import com.bb.reference.CustomerStatus;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.Size;
import java.util.Date;
import java.util.List;

@Entity
@Configurable
public class Customer {

    @NotNull
    @Size(min = 1, max = 30)
    @Column(unique = true)
    private String username;

    @NotNull
    @Size(min = 4, max = 15)
    private String password;

    @NotNull
    @Size(min = 5, max = 30)
    @Column(unique = true)
    private String email;

    @NotNull
    @Enumerated
    private CustomerStatus status;

    @Enumerated
    private CustomerRole customerRole;

    @Size(max = 30)
    private String name;

    @Size(max = 100)
    private String address;

    @Size(max = 30)
    private String city;

    @Size(max = 30)
    private String phone;

    @ManyToOne
    private RefSex sex;

    @Past
    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(style = "M-")
    private Date birthday;

    @Size(max = 500)
    private String bio;

    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(style = "MM")
    private Date registrationDate;

    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(style = "M-")
    private Date disableStartDate;

    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(style = "M-")
    private Date disableEndDate;

    @Size(max = 100)
    private String disableReason;

    @Transient
    private CommonsMultipartFile avatar;

    private Boolean hasAvatar;

    @Size(max = 50)
    private String activationCode;

    public CommonsMultipartFile getAvatar() {
        return avatar;
    }

    public void setAvatar(CommonsMultipartFile avatar) {
        this.avatar = avatar;
    }

    public Date getRegistrationDate() {
        if (this.registrationDate == null) {
            return new Date();
        } else {
            return this.registrationDate;
        }
    }

    public static TypedQuery<com.bb.domain.Customer> findCustomersByFieldExcludeById(String field, String value, Long id) {
        if (field == null || field.length() == 0) throw new IllegalArgumentException("The " + field + " argument is required");
        EntityManager em = entityManager();
        TypedQuery<Customer> q = em.createQuery("SELECT o FROM Customer AS o " + "WHERE o." + field + " = :" + field + " AND o.id!= :id", Customer.class);
        q.setParameter(field, value);
        q.setParameter("id", id);
        return q;
    }

    public static Customer findCustomersByUsernameOrEmail(String usernameOrEmail, String password) {
        if (usernameOrEmail == null || password == null) throw new IllegalArgumentException("The username/password argument is required");
        EntityManager em = entityManager();
        TypedQuery<Customer> q = em.createQuery("SELECT o FROM Customer AS o " + "WHERE (o.username = :username OR o.email = :email)  AND o.password= :password", Customer.class);
        q.setParameter("username", usernameOrEmail);
        q.setParameter("email", usernameOrEmail);
        q.setParameter("password", password);
        Customer customer = new Customer();
        try {
            customer = q.getSingleResult();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return customer;
    }

    public static Customer findCustomersByUsernameOrEmail(String usernameOrEmail) {
        if (usernameOrEmail == null ) throw new IllegalArgumentException("The username/password argument is required");
        EntityManager em = entityManager();
        TypedQuery<Customer> q = em.createQuery("SELECT o FROM Customer AS o " + "WHERE (o.username = :username OR o.email = :email)", Customer.class);
        q.setParameter("username", usernameOrEmail);
        q.setParameter("email", usernameOrEmail);
        Customer customer = new Customer();
        try {
            customer = q.getSingleResult();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return customer;
    }

    public static TypedQuery<Customer> findCustomersByCustomerRole(CustomerRole customerRole) {
        if (customerRole == null) throw new IllegalArgumentException("The customerRole argument is required");
        EntityManager em = entityManager();
        TypedQuery<Customer> q = em.createQuery("SELECT o FROM Customer AS o WHERE o.customerRole = :customerRole", Customer.class);
        q.setParameter("customerRole", customerRole);
        return q;
    }

    public static TypedQuery<Customer> findCustomersByEmail(String email) {
        if (email == null || email.length() == 0) throw new IllegalArgumentException("The email argument is required");
        EntityManager em = entityManager();
        TypedQuery<Customer> q = em.createQuery("SELECT o FROM Customer AS o WHERE o.email = :email", Customer.class);
        q.setParameter("email", email);
        return q;
    }

    public static TypedQuery<Customer> findCustomersByStatus(CustomerStatus status) {
        if (status == null) throw new IllegalArgumentException("The status argument is required");
        EntityManager em = entityManager();
        TypedQuery<Customer> q = em.createQuery("SELECT o FROM Customer AS o WHERE o.status = :status", Customer.class);
        q.setParameter("status", status);
        return q;
    }

    public static TypedQuery<Customer> findCustomersByUsername(String username) {
        if (username == null || username.length() == 0) throw new IllegalArgumentException("The username argument is required");
        EntityManager em = entityManager();
        TypedQuery<Customer> q = em.createQuery("SELECT o FROM Customer AS o WHERE o.username = :username", Customer.class);
        q.setParameter("username", username);
        return q;
    }

    public String getUsername() {
        return this.username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public CustomerStatus getStatus() {
        return this.status;
    }

    public void setStatus(CustomerStatus status) {
        this.status = status;
    }

    public CustomerRole getCustomerRole() {
        return this.customerRole;
    }

    public void setCustomerRole(CustomerRole customerRole) {
        this.customerRole = customerRole;
    }

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

    public String getPhone() {
        return this.phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public RefSex getSex() {
        return this.sex;
    }

    public void setSex(RefSex sex) {
        this.sex = sex;
    }

    public Date getBirthday() {
        return this.birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public String getBio() {
        return this.bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public void setRegistrationDate(Date registrationDate) {
        this.registrationDate = registrationDate;
    }

    public Date getDisableStartDate() {
        return this.disableStartDate;
    }

    public void setDisableStartDate(Date disableStartDate) {
        this.disableStartDate = disableStartDate;
    }

    public Date getDisableEndDate() {
        return this.disableEndDate;
    }

    public void setDisableEndDate(Date disableEndDate) {
        this.disableEndDate = disableEndDate;
    }

    public String getDisableReason() {
        return this.disableReason;
    }

    public void setDisableReason(String disableReason) {
        this.disableReason = disableReason;
    }

    public Boolean getHasAvatar() {
        return this.hasAvatar;
    }

    public void setHasAvatar(Boolean hasAvatar) {
        this.hasAvatar = hasAvatar;
    }

    public String getActivationCode() {
        return this.activationCode;
    }

    public void setActivationCode(String activationCode) {
        this.activationCode = activationCode;
    }


    public String toString() {
        return username;
    }

    @PersistenceContext
    transient EntityManager entityManager;

    public static final EntityManager entityManager() {
        EntityManager em = new Customer().entityManager;
        if (em == null) throw new IllegalStateException("Entity manager has not been injected (is the Spring Aspects JAR configured as an AJC/AJDT aspects library?)");
        return em;
    }

    public static long countCustomers() {
        return entityManager().createQuery("SELECT COUNT(o) FROM Customer o", Long.class).getSingleResult();
    }

    public static List<Customer> findAllCustomers() {
        return entityManager().createQuery("SELECT o FROM Customer o", Customer.class).getResultList();
    }

    public static Customer findCustomer(Long id) {
        if (id == null) return null;
        return entityManager().find(Customer.class, id);
    }

    public static List<Customer> findCustomerEntries(int firstResult, int maxResults) {
        return entityManager().createQuery("SELECT o FROM Customer o", Customer.class).setFirstResult(firstResult).setMaxResults(maxResults).getResultList();
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
            Customer attached = findCustomer(this.id);
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
    public Customer merge() {
        if (this.entityManager == null) this.entityManager = entityManager();
        Customer merged = this.entityManager.merge(this);
        this.entityManager.flush();
        return merged;
    }

    @Id
    @SequenceGenerator(name = "customerGen", sequenceName = "CUSTOMER_SEQ")
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "customerGen")
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
}
