package org.fasttrackit.onlineshop;


import org.fasttrackit.onlineshop.domain.Customer;
import org.fasttrackit.onlineshop.service.CustomerService;
import org.fasttrackit.onlineshop.steps.CustomerTestSteps;
import org.fasttrackit.onlineshop.transfer.customer.SaveCustomerRequest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.core.IsNull.notNullValue;

@SpringBootTest
public class CustomerServiceIntegrationTests {

    @Autowired
    private CustomerService customerService;

    @Autowired
    private CustomerTestSteps customerTestSteps;


    @Test
    void createCustomer_whenValidRequest_thenCustomerisCreated() {

        customerTestSteps.createCustomer();

    }

}
