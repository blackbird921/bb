// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package com.bb.domain;

import com.bb.domain.Customer;
import com.bb.domain.CustomerProduct;
import com.bb.domain.ProductCommit;
import com.bb.domain.ProductStake;
import java.util.Date;

privileged aspect CustomerProduct_Roo_JavaBean {
    
    public Customer CustomerProduct.getCustomer() {
        return this.customer;
    }
    
    public void CustomerProduct.setCustomer(Customer customer) {
        this.customer = customer;
    }
    
    public ProductCommit CustomerProduct.getProductCommit() {
        return this.productCommit;
    }
    
    public void CustomerProduct.setProductCommit(ProductCommit productCommit) {
        this.productCommit = productCommit;
    }
    
    public ProductStake CustomerProduct.getProductStake() {
        return this.productStake;
    }
    
    public void CustomerProduct.setProductStake(ProductStake productStake) {
        this.productStake = productStake;
    }
    
    public Date CustomerProduct.getStartDate() {
        return this.startDate;
    }
    
    public void CustomerProduct.setStartDate(Date startDate) {
        this.startDate = startDate;
    }
    
    public Date CustomerProduct.getEndDate() {
        return this.endDate;
    }
    
    public void CustomerProduct.setEndDate(Date endDate) {
        this.endDate = endDate;
    }
    
}
