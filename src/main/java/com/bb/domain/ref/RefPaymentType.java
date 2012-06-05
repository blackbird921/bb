package com.bb.domain.ref;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.jpa.activerecord.RooJpaActiveRecord;
import org.springframework.roo.addon.tostring.RooToString;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Configurable
@Entity
public class RefPaymentType {

    @NotNull
    @Size(min = 2, max = 30)
    private String name;

    @Override
    public String toString() {
        return name;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @PersistenceContext
    transient EntityManager entityManager;

    public static final EntityManager entityManager() {
        EntityManager em = new RefPaymentType().entityManager;
        if (em == null) throw new IllegalStateException("Entity manager has not been injected (is the Spring Aspects JAR configured as an AJC/AJDT aspects library?)");
        return em;
    }

    public static long countRefPaymentTypes() {
        return entityManager().createQuery("SELECT COUNT(o) FROM RefPaymentType o", Long.class).getSingleResult();
    }

    public static List<RefPaymentType> findAllRefPaymentTypes() {
        return entityManager().createQuery("SELECT o FROM RefPaymentType o", RefPaymentType.class).getResultList();
    }

    public static RefPaymentType findRefPaymentType(Long id) {
        if (id == null) return null;
        return entityManager().find(RefPaymentType.class, id);
    }

    public static List<RefPaymentType> findRefPaymentTypeEntries(int firstResult, int maxResults) {
        return entityManager().createQuery("SELECT o FROM RefPaymentType o", RefPaymentType.class).setFirstResult(firstResult).setMaxResults(maxResults).getResultList();
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
            RefPaymentType attached = findRefPaymentType(this.id);
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
    public RefPaymentType merge() {
        if (this.entityManager == null) this.entityManager = entityManager();
        RefPaymentType merged = this.entityManager.merge(this);
        this.entityManager.flush();
        return merged;
    }

    @Id
    @SequenceGenerator(name = "refPaymentTypeGen", sequenceName = "Ref_Payment_Type_SEQ")
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "refPaymentTypeGen")
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
