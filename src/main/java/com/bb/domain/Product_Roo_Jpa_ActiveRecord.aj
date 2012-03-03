// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package com.bb.domain;

import com.bb.domain.Product;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.springframework.transaction.annotation.Transactional;

privileged aspect Product_Roo_Jpa_ActiveRecord {
    
    @PersistenceContext
    transient EntityManager Product.entityManager;
    
    public static final EntityManager Product.entityManager() {
        EntityManager em = new Product().entityManager;
        if (em == null) throw new IllegalStateException("Entity manager has not been injected (is the Spring Aspects JAR configured as an AJC/AJDT aspects library?)");
        return em;
    }
    
    public static long Product.countProducts() {
        return entityManager().createQuery("SELECT COUNT(o) FROM Product o", Long.class).getSingleResult();
    }
    
    public static List<Product> Product.findAllProducts() {
        return entityManager().createQuery("SELECT o FROM Product o", Product.class).getResultList();
    }
    
    public static Product Product.findProduct(Long id) {
        if (id == null) return null;
        return entityManager().find(Product.class, id);
    }
    
    public static List<Product> Product.findProductEntries(int firstResult, int maxResults) {
        return entityManager().createQuery("SELECT o FROM Product o", Product.class).setFirstResult(firstResult).setMaxResults(maxResults).getResultList();
    }
    
    @Transactional
    public void Product.persist() {
        if (this.entityManager == null) this.entityManager = entityManager();
        this.entityManager.persist(this);
    }
    
    @Transactional
    public void Product.remove() {
        if (this.entityManager == null) this.entityManager = entityManager();
        if (this.entityManager.contains(this)) {
            this.entityManager.remove(this);
        } else {
            Product attached = Product.findProduct(this.id);
            this.entityManager.remove(attached);
        }
    }
    
    @Transactional
    public void Product.flush() {
        if (this.entityManager == null) this.entityManager = entityManager();
        this.entityManager.flush();
    }
    
    @Transactional
    public void Product.clear() {
        if (this.entityManager == null) this.entityManager = entityManager();
        this.entityManager.clear();
    }
    
    @Transactional
    public Product Product.merge() {
        if (this.entityManager == null) this.entityManager = entityManager();
        Product merged = this.entityManager.merge(this);
        this.entityManager.flush();
        return merged;
    }
    
}
