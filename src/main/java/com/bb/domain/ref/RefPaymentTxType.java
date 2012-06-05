package com.bb.domain.ref;

import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

@Configurable
@Entity
public class RefPaymentTxType {

    @NotNull
    @Size(min = 2, max = 30)
    private String name;

    @PersistenceContext
    transient EntityManager entityManager;

    @Id
    @SequenceGenerator(name = "refPaymentTxTypeGen", sequenceName = "Ref_Payment_Tx_Type_SEQ")
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "refPaymentTxTypeGen")
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


    public static final EntityManager entityManager() {
        EntityManager em = new RefPaymentTxType().entityManager;
        if (em == null) throw new IllegalStateException("Entity manager has not been injected (is the Spring Aspects JAR configured as an AJC/AJDT aspects library?)");
        return em;
    }

    public static long countRefPaymentTxTypes() {
        return entityManager().createQuery("SELECT COUNT(o) FROM RefPaymentTxType o", Long.class).getSingleResult();
    }

    public static List<RefPaymentTxType> findAllRefPaymentTxTypes() {
        return entityManager().createQuery("SELECT o FROM RefPaymentTxType o", RefPaymentTxType.class).getResultList();
    }

    public static RefPaymentTxType findRefPaymentTxType(Long id) {
        if (id == null) return null;
        return entityManager().find(RefPaymentTxType.class, id);
    }

    public static List<RefPaymentTxType> findRefPaymentTxTypeEntries(int firstResult, int maxResults) {
        return entityManager().createQuery("SELECT o FROM RefPaymentTxType o", RefPaymentTxType.class).setFirstResult(firstResult).setMaxResults(maxResults).getResultList();
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
            RefPaymentTxType attached = findRefPaymentTxType(this.id);
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
    public RefPaymentTxType merge() {
        if (this.entityManager == null) this.entityManager = entityManager();
        RefPaymentTxType merged = this.entityManager.merge(this);
        this.entityManager.flush();
        return merged;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }
}
