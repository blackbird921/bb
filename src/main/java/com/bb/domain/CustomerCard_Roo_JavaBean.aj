// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package com.bb.domain;

import com.bb.domain.Card;
import com.bb.domain.Customer;
import com.bb.domain.CustomerCard;
import java.util.Date;

privileged aspect CustomerCard_Roo_JavaBean {
    
    public Customer CustomerCard.getCustomer() {
        return this.customer;
    }
    
    public void CustomerCard.setCustomer(Customer customer) {
        this.customer = customer;
    }
    
    public Card CustomerCard.getCard() {
        return this.card;
    }
    
    public void CustomerCard.setCard(Card card) {
        this.card = card;
    }
    
    public Date CustomerCard.getUsedDate() {
        return this.usedDate;
    }
    
    public void CustomerCard.setUsedDate(Date usedDate) {
        this.usedDate = usedDate;
    }
    
    public Date CustomerCard.getIssuedDate() {
        return this.issuedDate;
    }
    
    public void CustomerCard.setIssuedDate(Date issuedDate) {
        this.issuedDate = issuedDate;
    }
    
    public String CustomerCard.getStatus() {
        return this.status;
    }
    
    public void CustomerCard.setStatus(String status) {
        this.status = status;
    }
    
    public boolean CustomerCard.isIsWizard() {
        return this.isWizard;
    }
    
    public void CustomerCard.setIsWizard(boolean isWizard) {
        this.isWizard = isWizard;
    }
    
}
