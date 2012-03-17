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
            if (cp.getStartDate() != null && cp.getStartDate().before(now) && (cp.getEndDate() == null || cp.getEndDate().after(now))) {
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
            if (cp.getStartDate() != null && cp.getStartDate().after(now)) {
                futureProduct = cp;
            } else if (cp.getStartDate() != null && cp.getStartDate().before(now) && (cp.getEndDate() == null || cp.getEndDate().after(now))) {
                currentProduct = cp;
            }

        }
        if (futureProduct == null) {
            futureProduct = currentProduct;
            futureProduct.setShowStartDate(false);
        }
        return futureProduct;
    }

    @Override
    public void updateFutureProduct(CustomerProduct customerProduct) {
        System.out.println("updateFutureProduct.........");
        CustomerProduct current = getCurrentProduct(customerProduct.getCustomer().getId());
        System.out.println("customerId=" + customerProduct.getCustomer().getId());
        if (current.getId().equals(customerProduct.getId())) {
            System.out.println("is Current");
            CustomerProduct future = new CustomerProduct();
            future.setCustomer(customerProduct.getCustomer());
            future.setProductCommit(customerProduct.getProductCommit());
            future.setProductStake(customerProduct.getProductStake());
            future.setStartDate(customerProduct.getStartDate());
            future.persist();
            System.out.println("saved");
        } else {
            System.out.println("is future");
            customerProduct.merge();
        }
    }

    public static void main(String[] args) {
//        System.out.println(new CustomerProductServiceImpl().(0L).getDaysLeft());
    }
}
