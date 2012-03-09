package com.bb.domain;

import com.bb.domain.ref.RefSex;
import com.bb.reference.CustomerRole;
import com.bb.reference.CustomerStatus;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.jpa.activerecord.RooJpaActiveRecord;
import org.springframework.roo.addon.tostring.RooToString;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.Size;
import java.util.Date;

@RooJavaBean
@RooToString
@RooJpaActiveRecord(sequenceName = "CUSTOMER_SEQ", finders = {"findCustomersByUsername", "findCustomersByEmail", "findCustomersByStatus", "findCustomersByCustomerRole"})
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

    public static TypedQuery<Customer> findCustomersByFieldExcludeById(String field, String value, Long id) {
        if (field == null || field.length() == 0)
            throw new IllegalArgumentException("The " + field + " argument is required");
        EntityManager em = Customer.entityManager();
        TypedQuery<Customer> q = em.createQuery("SELECT o FROM Customer AS o " +
                "WHERE o." + field + " = :" + field + " AND o.id!= :id", Customer.class);
        q.setParameter(field, value);
        q.setParameter("id", id);
        return q;
    }

}
