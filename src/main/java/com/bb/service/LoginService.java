package com.bb.service;

import com.bb.domain.Customer;
import com.bb.reference.CustomerLogin;
import org.apache.commons.codec.binary.Base64;
import org.springframework.dao.DataAccessException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Service(value = "loginService" )
public class LoginService implements UserDetailsService {

    public Long getCustomerId() {
        Object p = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (p == null) {
            return null;
        }

        CustomerLogin login = (CustomerLogin) p;
        return login.getCustomerId();
    }
    
    @Override
    public UserDetails loadUserByUsername(String usernameOrEmail) throws UsernameNotFoundException{
        Customer customer = Customer.findCustomersByUsernameOrEmail(usernameOrEmail);
        CustomerLogin customerLogin = null;
        if (customer != null) {
            customerLogin = new CustomerLogin();
            if (customer.getCustomerRole() != null) {
                SimpleGrantedAuthority authority = new SimpleGrantedAuthority(customer.getCustomerRole().name());
                List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
                authorities.add(authority);
                customerLogin.setAuthorities(authorities);
            }
            customerLogin.setUsername(usernameOrEmail);
            customerLogin.setPassword(customer.getPassword());
            customerLogin.setCustomerId(customer.getId());
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
