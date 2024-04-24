package com.commerce.payment.service.customer;

import com.commerce.payment.service.account.handler.CustomerRetrieveAllUseCaseHandler;
import com.commerce.payment.service.account.usecase.CustomerRetrieveAll;
import com.commerce.payment.service.customer.adapter.FakeCustomerDataAdapter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

/**
 * @Author mselvi
 * @Created 24.04.2024
 */

class CustomerRetrieveAllUseCaseHandlerTest {

    CustomerRetrieveAllUseCaseHandler customerRetrieveAllUseCaseHandler;

    @BeforeEach
    void setUp() {
        customerRetrieveAllUseCaseHandler = new CustomerRetrieveAllUseCaseHandler(new FakeCustomerDataAdapter());
    }

    @Test
    void should_retrieveAll() {
        //given
        var customerRetrieveAll = new CustomerRetrieveAll(Optional.of(0), Optional.of(3));

        //when
        var customerList = customerRetrieveAllUseCaseHandler.handle(customerRetrieveAll);

        //then
        assertEquals(customerRetrieveAll.size().get(),customerList.size());
        assertNotEquals(Collections.EMPTY_LIST.size(),customerList.size());
    }

    @Test
    void should_retrieveAll_empty() {
        //given
        var customerRetrieveAll = new CustomerRetrieveAll(Optional.of(0), Optional.of(0));

        //when
        var customerList = customerRetrieveAllUseCaseHandler.handle(customerRetrieveAll);

        //then
        assertEquals(customerRetrieveAll.size().get(),customerList.size());
        assertEquals(Collections.EMPTY_LIST.size(),customerList.size());
    }
}
