// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package com.bb.domain;

import com.bb.domain.Customer;
import com.bb.domain.CustomerPayment;
import com.bb.domain.ref.RefPaymentType;
import java.util.Date;

privileged aspect CustomerPayment_Roo_JavaBean {
    
    public Customer CustomerPayment.getCustomer() {
        return this.customer;
    }
    
    public void CustomerPayment.setCustomer(Customer customer) {
        this.customer = customer;
    }
    
    public RefPaymentType CustomerPayment.getPaymentType() {
        return this.paymentType;
    }
    
    public void CustomerPayment.setPaymentType(RefPaymentType paymentType) {
        this.paymentType = paymentType;
    }
    
    public String CustomerPayment.getAccountId() {
        return this.accountId;
    }
    
    public void CustomerPayment.setAccountId(String accountId) {
        this.accountId = accountId;
    }
    
    public Date CustomerPayment.getStartDate() {
        return this.startDate;
    }
    
    public void CustomerPayment.setStartDate(Date startDate) {
        this.startDate = startDate;
    }
    
    public Date CustomerPayment.getEndDate() {
        return this.endDate;
    }
    
    public void CustomerPayment.setEndDate(Date endDate) {
        this.endDate = endDate;
    }
    
}
