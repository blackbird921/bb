// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package com.bb.domain;

import com.bb.domain.Customer;
import com.bb.reference.CustomerRole;
import com.bb.reference.CustomerStatus;
import com.bb.reference.Sex;
import java.sql.Blob;
import java.util.Date;

privileged aspect Customer_Roo_JavaBean {
    
    public String Customer.getUsername() {
        return this.username;
    }
    
    public void Customer.setUsername(String username) {
        this.username = username;
    }
    
    public String Customer.getPassword() {
        return this.password;
    }
    
    public void Customer.setPassword(String password) {
        this.password = password;
    }
    
    public String Customer.getEmail() {
        return this.email;
    }
    
    public void Customer.setEmail(String email) {
        this.email = email;
    }
    
    public CustomerStatus Customer.getStatus() {
        return this.status;
    }
    
    public void Customer.setStatus(CustomerStatus status) {
        this.status = status;
    }
    
    public CustomerRole Customer.getCustomerRole() {
        return this.customerRole;
    }
    
    public void Customer.setCustomerRole(CustomerRole customerRole) {
        this.customerRole = customerRole;
    }
    
    public String Customer.getName() {
        return this.name;
    }
    
    public void Customer.setName(String name) {
        this.name = name;
    }
    
    public String Customer.getAddress() {
        return this.address;
    }
    
    public void Customer.setAddress(String address) {
        this.address = address;
    }
    
    public String Customer.getCity() {
        return this.city;
    }
    
    public void Customer.setCity(String city) {
        this.city = city;
    }
    
    public String Customer.getPhone() {
        return this.phone;
    }
    
    public void Customer.setPhone(String phone) {
        this.phone = phone;
    }
    
    public Sex Customer.getSex() {
        return this.sex;
    }
    
    public void Customer.setSex(Sex sex) {
        this.sex = sex;
    }
    
    public Date Customer.getBirthday() {
        return this.birthday;
    }
    
    public void Customer.setBirthday(Date birthday) {
        this.birthday = birthday;
    }
    
    public Blob Customer.getPicture() {
        return this.picture;
    }
    
    public void Customer.setPicture(Blob picture) {
        this.picture = picture;
    }
    
    public String Customer.getBio() {
        return this.bio;
    }
    
    public void Customer.setBio(String bio) {
        this.bio = bio;
    }
    
    public Date Customer.getRegistatrationDate() {
        return this.registatrationDate;
    }
    
    public void Customer.setRegistatrationDate(Date registatrationDate) {
        this.registatrationDate = registatrationDate;
    }
    
    public Date Customer.getDisableStartDate() {
        return this.disableStartDate;
    }
    
    public void Customer.setDisableStartDate(Date disableStartDate) {
        this.disableStartDate = disableStartDate;
    }
    
    public Date Customer.getDisableEndDate() {
        return this.disableEndDate;
    }
    
    public void Customer.setDisableEndDate(Date disableEndDate) {
        this.disableEndDate = disableEndDate;
    }
    
    public String Customer.getDisableReason() {
        return this.disableReason;
    }
    
    public void Customer.setDisableReason(String disableReason) {
        this.disableReason = disableReason;
    }
    
}
