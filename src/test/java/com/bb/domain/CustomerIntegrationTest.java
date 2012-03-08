package com.bb.domain;

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

    @Test
    public void testMarkerMethod() {
        CustomerDataOnDemand dod = new CustomerDataOnDemand();
        Customer c1 = dod.getNewTransientCustomer( 1 );
        c1.setUsername( "aaa" );
        c1.persist();

        assertTrue( validationService.existsUniqueValue( Customer.class, "username", "aaa" ) );
        assertFalse( validationService.existsUniqueValue( Customer.class, "username", "bbb" ) );
    }
}
