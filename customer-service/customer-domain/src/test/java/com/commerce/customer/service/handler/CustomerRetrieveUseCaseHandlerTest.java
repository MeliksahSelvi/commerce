package com.commerce.customer.service.handler;

import com.commerce.customer.service.common.exception.CustomerNotFoundException;
import com.commerce.customer.service.customer.handler.CustomerRetrieveUseCaseHandler;
import com.commerce.customer.service.customer.usecase.CustomerRetrieve;
import com.commerce.customer.service.handler.adapter.FakeCustomerDataPort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;


/**
 * @Author mselvi
 * @Created 20.03.2024
 */

class CustomerRetrieveUseCaseHandlerTest {

    CustomerRetrieveUseCaseHandler retrieveUseCaseHandler;

    @BeforeEach
    void setUp() {
        retrieveUseCaseHandler = new CustomerRetrieveUseCaseHandler(new FakeCustomerDataPort());
    }

    @Test
    void should_customer_retrieve() {
        //given
        var customerRetrieve = new CustomerRetrieve(1L);

        //when
        var customer = retrieveUseCaseHandler.handle(customerRetrieve);

        //then
        assertEquals(customerRetrieve.customerId(), customer.getId());
        assertNotNull(customer);
    }

    @Test
    void should_customer_retrieve_empty() {
        //given
        var customerRetrieve = new CustomerRetrieve(0L);

        //when
        //then
        assertThrows(CustomerNotFoundException.class, () -> retrieveUseCaseHandler.handle(customerRetrieve));
    }
}
