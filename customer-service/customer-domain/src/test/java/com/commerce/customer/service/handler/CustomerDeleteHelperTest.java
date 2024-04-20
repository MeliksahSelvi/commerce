package com.commerce.customer.service.handler;

import ch.qos.logback.classic.Level;
import com.commerce.customer.service.common.exception.CustomerNotFoundException;
import com.commerce.customer.service.customer.handler.helper.CustomerDeleteHelper;
import com.commerce.customer.service.customer.usecase.CustomerDelete;
import com.commerce.customer.service.handler.adapter.FakeCustomerDataAdapter;
import com.commerce.customer.service.handler.common.LoggerTest;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @Author mselvi
 * @Created 15.04.2024
 */

class CustomerDeleteHelperTest extends LoggerTest<CustomerDeleteHelper> {

    CustomerDeleteHelper deleteHelper;

    public CustomerDeleteHelperTest() {
        super(CustomerDeleteHelper.class);
    }

    @BeforeEach
    void setUp() {
        deleteHelper = new CustomerDeleteHelper(new FakeCustomerDataAdapter());
    }

    @AfterEach
    protected void cleanUp() {
        cleanUpActions();
    }

    @Test
    void should_delete() {
        //given
        var customerDelete = new CustomerDelete(1L);
        var logMessage = "Customer deleted by id: 1";

        //when
        //then
        assertDoesNotThrow(() -> deleteHelper.delete(customerDelete));
        assertTrue(memoryApender.contains(logMessage, Level.INFO));
    }

    @Test
    void should_delete_fail_when_customer_not_exist() {
        //given
        var customerDelete = new CustomerDelete(5L);

        //when
        //then
        var exception = assertThrows(CustomerNotFoundException.class, () -> deleteHelper.delete(customerDelete));
        assertTrue(exception.getMessage().contains("Customer Not Found By id: 5"));
    }


}
