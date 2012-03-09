// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package com.bb.domain;

import com.bb.domain.CustomerAvatar;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.springframework.transaction.annotation.Transactional;

privileged aspect CustomerAvatar_Roo_Jpa_ActiveRecord {
    
    @PersistenceContext
    transient EntityManager CustomerAvatar.entityManager;
    
    public static final EntityManager CustomerAvatar.entityManager() {
        EntityManager em = new CustomerAvatar().entityManager;
        if (em == null) throw new IllegalStateException("Entity manager has not been injected (is the Spring Aspects JAR configured as an AJC/AJDT aspects library?)");
        return em;
    }
    
    public static long CustomerAvatar.countCustomerAvatars() {
        return entityManager().createQuery("SELECT COUNT(o) FROM CustomerAvatar o", Long.class).getSingleResult();
    }
    
    public static List<CustomerAvatar> CustomerAvatar.findAllCustomerAvatars() {
        return entityManager().createQuery("SELECT o FROM CustomerAvatar o", CustomerAvatar.class).getResultList();
    }
    
    public static CustomerAvatar CustomerAvatar.findCustomerAvatar(Long id) {
        if (id == null) return null;
        return entityManager().find(CustomerAvatar.class, id);
    }
    
    public static List<CustomerAvatar> CustomerAvatar.findCustomerAvatarEntries(int firstResult, int maxResults) {
        return entityManager().createQuery("SELECT o FROM CustomerAvatar o", CustomerAvatar.class).setFirstResult(firstResult).setMaxResults(maxResults).getResultList();
    }
    
    @Transactional
    public void CustomerAvatar.persist() {
        if (this.entityManager == null) this.entityManager = entityManager();
        this.entityManager.persist(this);
    }
    
    @Transactional
    public void CustomerAvatar.remove() {
        if (this.entityManager == null) this.entityManager = entityManager();
        if (this.entityManager.contains(this)) {
            this.entityManager.remove(this);
        } else {
            CustomerAvatar attached = CustomerAvatar.findCustomerAvatar(this.id);
            this.entityManager.remove(attached);
        }
    }
    
    @Transactional
    public void CustomerAvatar.flush() {
        if (this.entityManager == null) this.entityManager = entityManager();
        this.entityManager.flush();
    }
    
    @Transactional
    public void CustomerAvatar.clear() {
        if (this.entityManager == null) this.entityManager = entityManager();
        this.entityManager.clear();
    }
    
    @Transactional
    public CustomerAvatar CustomerAvatar.merge() {
        if (this.entityManager == null) this.entityManager = entityManager();
        CustomerAvatar merged = this.entityManager.merge(this);
        this.entityManager.flush();
        return merged;
    }
    
}
