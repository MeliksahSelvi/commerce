package com.commerce.customer.service.handler;

import ch.qos.logback.classic.Level;
import com.commerce.customer.service.common.exception.CustomerNotFoundException;
import com.commerce.customer.service.customer.handler.CustomerDeleteVoidUseCaseHandler;
import com.commerce.customer.service.customer.usecase.CustomerDelete;
import com.commerce.customer.service.handler.adapter.FakeCustomerDeleteHelper;
import com.commerce.customer.service.handler.common.LoggerTest;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @Author mselvi
 * @Created 15.04.2024
 */

class CustomerDeleteVoidUseCaseHandlerTest extends LoggerTest<CustomerDeleteVoidUseCaseHandler> {

    CustomerDeleteVoidUseCaseHandler handler;

    public CustomerDeleteVoidUseCaseHandlerTest() {
        super(CustomerDeleteVoidUseCaseHandler.class);
    }

    @BeforeEach
    void setUp() {
        handler = new CustomerDeleteVoidUseCaseHandler(new FakeCustomerDeleteHelper());
    }

    @AfterEach
    protected void cleanUp() {
        cleanUpActions();
    }

    @Test
    void should_handle() {
        //given
        var customerDelete = new CustomerDelete(1L);
        var logMessage="Customer delete action started by id: 1";

        //when
        //then
        assertDoesNotThrow(() -> handler.handle(customerDelete));
        assertTrue(memoryApender.contains(logMessage, Level.INFO));
    }


    @Test
    void should_not_handle_when_customer_not_exist(){
        //given
        var customerDelete = new CustomerDelete(5L);
        var logMessage="Customer delete action started by id: 5";

        //when
        //then
        var customerNotFoundException = assertThrows(CustomerNotFoundException.class, () -> handler.handle(customerDelete));
        assertTrue(customerNotFoundException.getMessage().contains("Customer Not Found By id: 5"));
        assertTrue(memoryApender.contains(logMessage, Level.INFO));
    }
}