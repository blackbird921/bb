package com.bb.service;

import com.bb.domain.Customer;
import com.bb.reference.CustomerLogin;
import org.apache.commons.codec.binary.Base64;
import org.springframework.dao.DataAccessException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;


@Service(value = "loginService" )
public class LoginService implements UserDetailsService {

    
    @Override
    public UserDetails loadUserByUsername(String usernameOrEmail) throws UsernameNotFoundException{
        Customer customer = Customer.findCustomersByUsernameOrEmail(usernameOrEmail);
        CustomerLogin customerLogin = null;
        if (customer != null) {
            customerLogin = new CustomerLogin();
            customerLogin.setUsername(usernameOrEmail);
            customerLogin.setPassword(customer.getPassword());
        }


        if (customerLogin == null) {
            throw new UsernameNotFoundException(usernameOrEmail);
        }
        return customerLogin;
    }


    public String generateActivationCode(String email) {

        String code = (email.length() >= 6 ? email.substring(0, 6) : email) + System.currentTimeMillis();
        code = Base64.encodeBase64String(code.getBytes());
        return code.substring(0, 16);
    }

    public void changePassword(Long cid, String oldOne, String newOne) {
        Customer customer = Customer.findCustomer(cid);
        if (customer.getPassword().equals(oldOne)) {
            customer.setPassword(newOne);
        }
        customer.merge();

    }

}
