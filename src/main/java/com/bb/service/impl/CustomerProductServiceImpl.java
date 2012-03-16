package com.bb.service.impl;

import com.bb.domain.CustomerProduct;
import com.bb.service.CustomerProductService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Service
@Transactional
public class CustomerProductServiceImpl implements CustomerProductService {

    @Override
    public CustomerProduct getCurrentProduct(Long cid) {
        List<CustomerProduct> all = CustomerProduct.findAllCustomerProducts();
        CustomerProduct product = null;
        Date now = Calendar.getInstance().getTime();
        for (CustomerProduct cp : all) {
            if (cp.getStartDate().before(now) && (cp.getEndDate().after(now) || cp.getEndDate() == null)) {
                product = cp;
            }
        }
        return product;
    }

    @Override
    public CustomerProduct getFutureProduct(Long cid) {
        List<CustomerProduct> all = CustomerProduct.findAllCustomerProducts();
        CustomerProduct futureProduct = null;
        CustomerProduct currentProduct = null;
        Date now = Calendar.getInstance().getTime();
        for (CustomerProduct cp : all) {
            if (cp.getStartDate().after(now)) {
                futureProduct = cp;
            } else if (cp.getStartDate().before(now) && (cp.getEndDate().after(now) || cp.getEndDate() == null)) {
                currentProduct = cp;
            }

        }
        if (futureProduct == null) {
            futureProduct = currentProduct;
        }
        return futureProduct;
    }


    public static void main(String[] args) {
//        System.out.println(new CustomerProductServiceImpl().(0L).getDaysLeft());
    }
}
