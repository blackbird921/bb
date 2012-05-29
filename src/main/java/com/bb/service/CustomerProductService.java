package com.bb.service;

import com.bb.domain.CustomerProduct;
import com.bb.reference.WeekStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Service
@Transactional
public class CustomerProductService {

    public CustomerProduct getCurrentProduct(Long cid) {
        return getPastProduct(cid, 0);
    }

    public CustomerProduct getPastProduct(Long cid, Integer lastNWeek) {
        List<CustomerProduct> all = CustomerProduct.findAllByCustomerId(cid);
        CustomerProduct product = null;
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, -7*lastNWeek);
        Date now = cal.getTime();
        for (CustomerProduct cp : all) {
            System.out.println("now="+now);
            System.out.println(cp.getStartDate());
            System.out.println(cp.getEndDate());
            if (cp.getStartDate() != null && cp.getStartDate().before(now) && (cp.getEndDate() == null || cp.getEndDate().after(now))) {
                product = cp;
            }
        }
        return product;
    }

    public CustomerProduct getFutureProduct(Long cid) {
        List<CustomerProduct> all = CustomerProduct.findAllByCustomerId(cid);
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
