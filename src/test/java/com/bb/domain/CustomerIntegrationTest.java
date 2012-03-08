package com.bb.domain;

import org.junit.Test;
import org.springframework.roo.addon.test.RooIntegrationTest;

@RooIntegrationTest(entity = Customer.class)
public class CustomerIntegrationTest {

    @Test
    public void testMarkerMethod() {
        CustomerDataOnDemand dod = new CustomerDataOnDemand();
        Customer c1 = dod.getNewTransientCustomer( 1 );
        c1.setUsername( "aaa" );
        c1.persist();

        Customer c2 = dod.getNewTransientCustomer( 2 );
        c2.setUsername( "aaa" );
        c2.persist();
    }
}
