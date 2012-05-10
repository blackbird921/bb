package com.bb.service;

import com.bb.domain.CustomerCheckin;
import com.bb.domain.CustomerProduct;
import com.bb.domain.CustomerProfit;
import com.bb.reference.CustomerStats;
import com.bb.util.AutowiredLogger;
import org.slf4j.Logger;
import org.springframework.stereotype.Service;

@Service
public class ReportService {
   @AutowiredLogger
   Logger logger;
   

    public CustomerStats getCustomerStats(Long cid) {
        CustomerStats customerStats = new CustomerStats();
        customerStats.setBonusTotal(CustomerProfit.countProfitByCustomerId(cid).getSingleResult().intValue());
        customerStats.setBonusRank(CustomerProfit.getProfitRankByCustomerId(cid));

        int checkin = CustomerCheckin.findCustomerCheckinsByCustomerAndApproved(cid).getResultList().size();
        int commits = CustomerProduct.getAllCommitsByCustomerId(cid);

        customerStats.setCheckinTotal(checkin);
        customerStats.setCommitTotal(commits);
        customerStats.setCheckinPercentage((int) (((float) checkin / (float) commits) * 100));
        customerStats.setCheckinRank(CustomerCheckin.getCompletionRateRankByCustomer(cid));
        
        logger.info("bonusTotal={}", customerStats.getBonusTotal());
        return customerStats;
    }

    public static void main(String[] args) {
        System.out.println();
    }
}
