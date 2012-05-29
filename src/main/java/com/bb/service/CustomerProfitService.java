package com.bb.service;

import com.bb.domain.CustomerProfit;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Service
@Transactional
public class CustomerProfitService {

    public CustomerProfit getPastProfit(Long cid, Integer lastNWeek) {
        List<CustomerProfit> all = CustomerProfit.findAllProfitByCustomerId(cid);
        CustomerProfit profit = null;
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, -7*lastNWeek);
        Date now = cal.getTime();
        for (CustomerProfit cp : all) {
            if (cp.getStartDate() != null && cp.getStartDate().before(now) && (cp.getEndDate() == null || cp.getEndDate().after(now))) {
                profit = cp;
            }
        }
        return profit;
    }


    public static void main(String[] args) {
//        System.out.println(new CustomerProfitServiceImpl().(0L).getDaysLeft());
    }
}
