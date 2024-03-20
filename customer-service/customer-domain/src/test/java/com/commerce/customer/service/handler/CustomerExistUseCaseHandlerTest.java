package com.commerce.customer.service.handler;

import com.commerce.customer.service.customer.handler.CustomerExistUseCaseHandler;
import com.commerce.customer.service.customer.usecase.CustomerRetrieve;
import com.commerce.customer.service.handler.adapter.FakeCustomerDataPort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * @Author mselvi
 * @Created 20.03.2024
 */

class CustomerExistUseCaseHandlerTest {

    CustomerExistUseCaseHandler customerExistUseCaseHandler;

    @BeforeEach
    void setUp() {
        customerExistUseCaseHandler = new CustomerExistUseCaseHandler(new FakeCustomerDataPort());
    }

    @Test
    void should_customer_exist() {
        //given
        var customerRetrieve = new CustomerRetrieve(1L);

        //when
        var response = customerExistUseCaseHandler.handle(customerRetrieve);

        //then
        assertTrue(response);
    }

    @Test
    void should_customer_not_exist() {
        //given
        var customerRetrieve = new CustomerRetrieve(0L);

        //when
        var response = customerExistUseCaseHandler.handle(customerRetrieve);

        //then
        assertFalse(response);
    }
}
