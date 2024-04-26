package com.commerce.payment.service.customer;

import com.commerce.payment.service.customer.handler.CustomerRetrieveUseCaseHandler;
import com.commerce.payment.service.customer.usecase.CustomerRetrieve;
import com.commerce.payment.service.common.exception.CustomerNotFoundException;
import com.commerce.payment.service.customer.adapter.FakeCustomerDataAdapter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * @Author mselvi
 * @Created 24.04.2024
 */

class CustomerRetrieveUseCaseHandlerTest {

    CustomerRetrieveUseCaseHandler retrieveUseCaseHandler;

    @BeforeEach
    void setUp() {
        retrieveUseCaseHandler = new CustomerRetrieveUseCaseHandler(new FakeCustomerDataAdapter());
    }

    @Test
    void should_retrieve() {
        //given
        var customerRetrieve = new CustomerRetrieve(1L);


        //when
        //then
        var customer=assertDoesNotThrow(()->retrieveUseCaseHandler.handle(customerRetrieve));
        assertEquals(customerRetrieve.customerId(), customer.getId());
        assertNotNull(customer);
    }

    @Test
    void should_retrieve_empty() {
        //given
        var customerRetrieve = new CustomerRetrieve(0L);
        var errorMessage="Customer Not Found By id: 0";

        //when
        //then
        var customerNotFoundException = assertThrows(CustomerNotFoundException.class, () -> retrieveUseCaseHandler.handle(customerRetrieve));
        assertTrue(customerNotFoundException.getMessage().contains(errorMessage));
    }
}
