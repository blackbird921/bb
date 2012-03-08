// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package com.bb.domain;

import com.bb.domain.Customer;
import com.bb.domain.CustomerDataOnDemand;
import com.bb.domain.ref.RefSex;
import com.bb.reference.CustomerRole;
import com.bb.reference.CustomerStatus;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import org.springframework.stereotype.Component;

privileged aspect CustomerDataOnDemand_Roo_DataOnDemand {
    
    declare @type: CustomerDataOnDemand: @Component;
    
    private Random CustomerDataOnDemand.rnd = new SecureRandom();
    
    private List<Customer> CustomerDataOnDemand.data;
    
    public Customer CustomerDataOnDemand.getNewTransientCustomer(int index) {
        Customer obj = new Customer();
        setAddress(obj, index);
        setBio(obj, index);
        setBirthday(obj, index);
        setCity(obj, index);
        setCustomerRole(obj, index);
        setDisableEndDate(obj, index);
        setDisableReason(obj, index);
        setDisableStartDate(obj, index);
        setEmail(obj, index);
        setName(obj, index);
        setPassword(obj, index);
        setPhone(obj, index);
        setRegistrationDate(obj, index);
        setSex(obj, index);
        setStatus(obj, index);
        setUsername(obj, index);
        return obj;
    }
    
    public void CustomerDataOnDemand.setAddress(Customer obj, int index) {
        String address = "address_" + index;
        if (address.length() > 100) {
            address = address.substring(0, 100);
        }
        obj.setAddress(address);
    }
    
    public void CustomerDataOnDemand.setBio(Customer obj, int index) {
        String bio = "bio_" + index;
        if (bio.length() > 500) {
            bio = bio.substring(0, 500);
        }
        obj.setBio(bio);
    }
    
    public void CustomerDataOnDemand.setBirthday(Customer obj, int index) {
        Date birthday = new Date(new Date().getTime() - 10000000L);
        obj.setBirthday(birthday);
    }
    
    public void CustomerDataOnDemand.setCity(Customer obj, int index) {
        String city = "city_" + index;
        if (city.length() > 30) {
            city = city.substring(0, 30);
        }
        obj.setCity(city);
    }
    
    public void CustomerDataOnDemand.setCustomerRole(Customer obj, int index) {
        CustomerRole customerRole = CustomerRole.class.getEnumConstants()[0];
        obj.setCustomerRole(customerRole);
    }
    
    public void CustomerDataOnDemand.setDisableEndDate(Customer obj, int index) {
        Date disableEndDate = new GregorianCalendar(Calendar.getInstance().get(Calendar.YEAR), Calendar.getInstance().get(Calendar.MONTH), Calendar.getInstance().get(Calendar.DAY_OF_MONTH), Calendar.getInstance().get(Calendar.HOUR_OF_DAY), Calendar.getInstance().get(Calendar.MINUTE), Calendar.getInstance().get(Calendar.SECOND) + new Double(Math.random() * 1000).intValue()).getTime();
        obj.setDisableEndDate(disableEndDate);
    }
    
    public void CustomerDataOnDemand.setDisableReason(Customer obj, int index) {
        String disableReason = "disableReason_" + index;
        if (disableReason.length() > 100) {
            disableReason = disableReason.substring(0, 100);
        }
        obj.setDisableReason(disableReason);
    }
    
    public void CustomerDataOnDemand.setDisableStartDate(Customer obj, int index) {
        Date disableStartDate = new GregorianCalendar(Calendar.getInstance().get(Calendar.YEAR), Calendar.getInstance().get(Calendar.MONTH), Calendar.getInstance().get(Calendar.DAY_OF_MONTH), Calendar.getInstance().get(Calendar.HOUR_OF_DAY), Calendar.getInstance().get(Calendar.MINUTE), Calendar.getInstance().get(Calendar.SECOND) + new Double(Math.random() * 1000).intValue()).getTime();
        obj.setDisableStartDate(disableStartDate);
    }
    
    public void CustomerDataOnDemand.setEmail(Customer obj, int index) {
        String email = "foo" + index + "@bar.com";
        if (email.length() > 30) {
            email = new Random().nextInt(10) + email.substring(1, 30);
        }
        obj.setEmail(email);
    }
    
    public void CustomerDataOnDemand.setName(Customer obj, int index) {
        String name = "name_" + index;
        if (name.length() > 30) {
            name = name.substring(0, 30);
        }
        obj.setName(name);
    }
    
    public void CustomerDataOnDemand.setPassword(Customer obj, int index) {
        String password = "password_" + index;
        if (password.length() > 15) {
            password = password.substring(0, 15);
        }
        obj.setPassword(password);
    }
    
    public void CustomerDataOnDemand.setPhone(Customer obj, int index) {
        String phone = "phone_" + index;
        if (phone.length() > 30) {
            phone = phone.substring(0, 30);
        }
        obj.setPhone(phone);
    }
    
    public void CustomerDataOnDemand.setRegistrationDate(Customer obj, int index) {
        Date registrationDate = new GregorianCalendar(Calendar.getInstance().get(Calendar.YEAR), Calendar.getInstance().get(Calendar.MONTH), Calendar.getInstance().get(Calendar.DAY_OF_MONTH), Calendar.getInstance().get(Calendar.HOUR_OF_DAY), Calendar.getInstance().get(Calendar.MINUTE), Calendar.getInstance().get(Calendar.SECOND) + new Double(Math.random() * 1000).intValue()).getTime();
        obj.setRegistrationDate(registrationDate);
    }
    
    public void CustomerDataOnDemand.setSex(Customer obj, int index) {
        RefSex sex = null;
        obj.setSex(sex);
    }
    
    public void CustomerDataOnDemand.setStatus(Customer obj, int index) {
        CustomerStatus status = CustomerStatus.class.getEnumConstants()[0];
        obj.setStatus(status);
    }
    
    public void CustomerDataOnDemand.setUsername(Customer obj, int index) {
        String username = "username_" + index;
        if (username.length() > 30) {
            username = new Random().nextInt(10) + username.substring(1, 30);
        }
        obj.setUsername(username);
    }
    
    public Customer CustomerDataOnDemand.getSpecificCustomer(int index) {
        init();
        if (index < 0) {
            index = 0;
        }
        if (index > (data.size() - 1)) {
            index = data.size() - 1;
        }
        Customer obj = data.get(index);
        Long id = obj.getId();
        return Customer.findCustomer(id);
    }
    
    public Customer CustomerDataOnDemand.getRandomCustomer() {
        init();
        Customer obj = data.get(rnd.nextInt(data.size()));
        Long id = obj.getId();
        return Customer.findCustomer(id);
    }
    
    public boolean CustomerDataOnDemand.modifyCustomer(Customer obj) {
        return false;
    }
    
    public void CustomerDataOnDemand.init() {
        int from = 0;
        int to = 10;
        data = Customer.findCustomerEntries(from, to);
        if (data == null) {
            throw new IllegalStateException("Find entries implementation for 'Customer' illegally returned null");
        }
        if (!data.isEmpty()) {
            return;
        }
        
        data = new ArrayList<Customer>();
        for (int i = 0; i < 10; i++) {
            Customer obj = getNewTransientCustomer(i);
            try {
                obj.persist();
            } catch (ConstraintViolationException e) {
                StringBuilder msg = new StringBuilder();
                for (Iterator<ConstraintViolation<?>> iter = e.getConstraintViolations().iterator(); iter.hasNext();) {
                    ConstraintViolation<?> cv = iter.next();
                    msg.append("[").append(cv.getConstraintDescriptor()).append(":").append(cv.getMessage()).append("=").append(cv.getInvalidValue()).append("]");
                }
                throw new RuntimeException(msg.toString(), e);
            }
            obj.flush();
            data.add(obj);
        }
    }
    
}