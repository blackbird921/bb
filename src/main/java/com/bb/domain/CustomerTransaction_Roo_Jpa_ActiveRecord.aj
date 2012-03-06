// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package com.bb.domain;

import com.bb.domain.CustomerTransaction;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.springframework.transaction.annotation.Transactional;

privileged aspect CustomerTransaction_Roo_Jpa_ActiveRecord {
    
    @PersistenceContext
    transient EntityManager CustomerTransaction.entityManager;
    
    public static final EntityManager CustomerTransaction.entityManager() {
        EntityManager em = new CustomerTransaction().entityManager;
        if (em == null) throw new IllegalStateException("Entity manager has not been injected (is the Spring Aspects JAR configured as an AJC/AJDT aspects library?)");
        return em;
    }
    
    public static long CustomerTransaction.countCustomerTransactions() {
        return entityManager().createQuery("SELECT COUNT(o) FROM CustomerTransaction o", Long.class).getSingleResult();
    }
    
    public static List<CustomerTransaction> CustomerTransaction.findAllCustomerTransactions() {
        return entityManager().createQuery("SELECT o FROM CustomerTransaction o", CustomerTransaction.class).getResultList();
    }
    
    public static CustomerTransaction CustomerTransaction.findCustomerTransaction(Long id) {
        if (id == null) return null;
        return entityManager().find(CustomerTransaction.class, id);
    }
    
    public static List<CustomerTransaction> CustomerTransaction.findCustomerTransactionEntries(int firstResult, int maxResults) {
        return entityManager().createQuery("SELECT o FROM CustomerTransaction o", CustomerTransaction.class).setFirstResult(firstResult).setMaxResults(maxResults).getResultList();
    }
    
    @Transactional
    public void CustomerTransaction.persist() {
        if (this.entityManager == null) this.entityManager = entityManager();
        this.entityManager.persist(this);
    }
    
    @Transactional
    public void CustomerTransaction.remove() {
        if (this.entityManager == null) this.entityManager = entityManager();
        if (this.entityManager.contains(this)) {
            this.entityManager.remove(this);
        } else {
            CustomerTransaction attached = CustomerTransaction.findCustomerTransaction(this.id);
            this.entityManager.remove(attached);
        }
    }
    
    @Transactional
    public void CustomerTransaction.flush() {
        if (this.entityManager == null) this.entityManager = entityManager();
        this.entityManager.flush();
    }
    
    @Transactional
    public void CustomerTransaction.clear() {
        if (this.entityManager == null) this.entityManager = entityManager();
        this.entityManager.clear();
    }
    
    @Transactional
    public CustomerTransaction CustomerTransaction.merge() {
        if (this.entityManager == null) this.entityManager = entityManager();
        CustomerTransaction merged = this.entityManager.merge(this);
        this.entityManager.flush();
        return merged;
    }
    
}