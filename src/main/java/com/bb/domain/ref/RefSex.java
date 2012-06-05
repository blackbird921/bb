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
public class RefSex {

    @NotNull
    @Size(max = 30)
    private String name;

    @Override
    public String toString() {
        return name;
    }
    @PersistenceContext
    transient EntityManager entityManager;

    public static final EntityManager entityManager() {
        EntityManager em = new RefSex().entityManager;
        if (em == null) throw new IllegalStateException("Entity manager has not been injected (is the Spring Aspects JAR configured as an AJC/AJDT aspects library?)");
        return em;
    }

    public static long countRefSexes() {
        return entityManager().createQuery("SELECT COUNT(o) FROM RefSex o", Long.class).getSingleResult();
    }

    public static List<RefSex> findAllRefSexes() {
        return entityManager().createQuery("SELECT o FROM RefSex o", RefSex.class).getResultList();
    }

    public static RefSex findRefSex(Long id) {
        if (id == null) return null;
        return entityManager().find(RefSex.class, id);
    }

    public static List<RefSex> findRefSexEntries(int firstResult, int maxResults) {
        return entityManager().createQuery("SELECT o FROM RefSex o", RefSex.class).setFirstResult(firstResult).setMaxResults(maxResults).getResultList();
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
            RefSex attached = findRefSex(this.id);
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
    public RefSex merge() {
        if (this.entityManager == null) this.entityManager = entityManager();
        RefSex merged = this.entityManager.merge(this);
        this.entityManager.flush();
        return merged;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public static TypedQuery<RefSex> findRefSexesByName(String name) {
        if (name == null || name.length() == 0) throw new IllegalArgumentException("The name argument is required");
        EntityManager em = entityManager();
        TypedQuery<RefSex> q = em.createQuery("SELECT o FROM RefSex AS o WHERE o.name = :name", RefSex.class);
        q.setParameter("name", name);
        return q;
    }

    @Id
    @SequenceGenerator(name = "refSexGen", sequenceName = "Ref_Sex_SEQ")
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "refSexGen")
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
