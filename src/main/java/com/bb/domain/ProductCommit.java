package com.bb.domain;

import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.List;

@Configurable
@Entity
public class ProductCommit {

    @NotNull
    @Min(1L)
    @Max(7L)
    private Long commits;

    private Boolean isActive;

    @Override
    public String toString() {
        return commits.toString();
    }

    @Id
    @SequenceGenerator(name = "productCommitGen", sequenceName = "Product_Commit_SEQ")
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "productCommitGen")
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
    @PersistenceContext
    transient EntityManager entityManager;

    public static final EntityManager entityManager() {
        EntityManager em = new ProductCommit().entityManager;
        if (em == null) throw new IllegalStateException("Entity manager has not been injected (is the Spring Aspects JAR configured as an AJC/AJDT aspects library?)");
        return em;
    }

    public static long countProductCommits() {
        return entityManager().createQuery("SELECT COUNT(o) FROM ProductCommit o", Long.class).getSingleResult();
    }

    public static List<ProductCommit> findAllProductCommits() {
        return entityManager().createQuery("SELECT o FROM ProductCommit o", ProductCommit.class).getResultList();
    }

    public static ProductCommit findProductCommit(Long id) {
        if (id == null) return null;
        return entityManager().find(ProductCommit.class, id);
    }

    public static List<ProductCommit> findProductCommitEntries(int firstResult, int maxResults) {
        return entityManager().createQuery("SELECT o FROM ProductCommit o", ProductCommit.class).setFirstResult(firstResult).setMaxResults(maxResults).getResultList();
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
            ProductCommit attached = findProductCommit(this.id);
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
    public ProductCommit merge() {
        if (this.entityManager == null) this.entityManager = entityManager();
        ProductCommit merged = this.entityManager.merge(this);
        this.entityManager.flush();
        return merged;
    }
    public Long getCommits() {
        return this.commits;
    }

    public void setCommits(Long commits) {
        this.commits = commits;
    }

    public Boolean getIsActive() {
        return this.isActive;
    }

    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;
    }

}
