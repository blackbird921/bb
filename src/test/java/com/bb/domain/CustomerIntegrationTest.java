package com.bb.domain;

import com.bb.reference.CustomerStats;
import com.bb.reference.CustomerStatus;
import com.bb.service.ReportService;
import com.bb.service.ValidationService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.roo.addon.test.RooIntegrationTest;

import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertTrue;

@RooIntegrationTest( entity = Customer.class )
public class CustomerIntegrationTest {
    @Autowired
    ValidationService validationService;
    @Autowired
    ReportService reportService;

    @Test
    public void testCustomerCrud() {
        CustomerDataOnDemand dod = new CustomerDataOnDemand();
        Customer c1 = dod.getNewTransientCustomer( 1 );
        c1.setUsername( "aaa" );
        c1.setStatus(CustomerStatus.Trial);
        c1.persist();

        assertFalse( validationService.existsUniqueValue( Customer.class, "username", "aaa", c1.getId() ) );
        assertFalse( validationService.existsUniqueValue( Customer.class, "username", "bbb", c1.getId() ) );

        Customer c2 = dod.getNewTransientCustomer( 2 );
        c2.setUsername( "aaa" );
        c2.setStatus(CustomerStatus.Trial);
        assertTrue( validationService.existsUniqueValue( Customer.class, "username", "aaa", c2.getId() ) );
    }

    @Test
    public void getCustomerStats() {
        CustomerStats customerStats = reportService.getCustomerStats(1L);
        System.out.println("bonusTotal="+customerStats.getBonusTotal());
        System.out.println("bonusRank="+customerStats.getBonusRank());
        System.out.println("checkinPercentage="+customerStats.getCheckinPercentage());
        System.out.println("checkinRank="+customerStats.getCheckinRank());
    }
}

