package com.bb.domain;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.jpa.activerecord.RooJpaActiveRecord;
import org.springframework.roo.addon.tostring.RooToString;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Configurable
@Entity
public class ProductStake {

    @NotNull
    @Min(5L)
    @Max(50L)
    private Long stakes;

    private Boolean isActive;

    @Override
    public String toString() {
        return stakes.toString();
    }
    @Id
    @SequenceGenerator(name = "productStakeGen", sequenceName = "Product_Stake_SEQ")
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "productStakeGen")
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

    public Long getStakes() {
        return this.stakes;
    }

    public void setStakes(Long stakes) {
        this.stakes = stakes;
    }

    public Boolean getIsActive() {
        return this.isActive;
    }

    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;
    }

    @PersistenceContext
    transient EntityManager entityManager;

    public static final EntityManager entityManager() {
        EntityManager em = new ProductStake().entityManager;
        if (em == null) throw new IllegalStateException("Entity manager has not been injected (is the Spring Aspects JAR configured as an AJC/AJDT aspects library?)");
        return em;
    }

    public static long countProductStakes() {
        return entityManager().createQuery("SELECT COUNT(o) FROM ProductStake o", Long.class).getSingleResult();
    }

    public static List<ProductStake> findAllProductStakes() {
        return entityManager().createQuery("SELECT o FROM ProductStake o", ProductStake.class).getResultList();
    }

    public static ProductStake findProductStake(Long id) {
        if (id == null) return null;
        return entityManager().find(ProductStake.class, id);
    }

    public static List<ProductStake> findProductStakeEntries(int firstResult, int maxResults) {
        return entityManager().createQuery("SELECT o FROM ProductStake o", ProductStake.class).setFirstResult(firstResult).setMaxResults(maxResults).getResultList();
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
            ProductStake attached = findProductStake(this.id);
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
    public ProductStake merge() {
        if (this.entityManager == null) this.entityManager = entityManager();
        ProductStake merged = this.entityManager.merge(this);
        this.entityManager.flush();
        return merged;
    }
}
