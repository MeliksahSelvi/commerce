package com.commerce.notification.service.customer.adapters.messaging;

import ch.qos.logback.classic.Level;
import com.commerce.notification.service.common.LoggerTest;
import com.commerce.notification.service.customer.adapters.messaging.adapter.FakeCustomerDataAdapter;
import com.commerce.notification.service.customer.adapters.messaging.helper.CustomerCommandListenerHelper;
import com.commerce.notification.service.customer.usecase.CustomerInfo;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * @Author mselvi
 * @Created 26.04.2024
 */

class CustomerCommandListenerHelperTest extends LoggerTest<CustomerCommandListenerHelper> {

    CustomerCommandListenerHelper helper;

    public CustomerCommandListenerHelperTest() {
        super(CustomerCommandListenerHelper.class);
    }

    @BeforeEach
    void setUp() {
        helper = new CustomerCommandListenerHelper(new FakeCustomerDataAdapter());
    }

    @AfterEach
    protected void cleanUp() {
        cleanUpActions();
    }

    @Test
    void should_processMessage() {
        //given
        var message = new CustomerInfo(1L, "name", "surname", "email@gmail.com");
        var logMessage = "Customer saved by email: email@gmail.com";

        //when
        //then
        assertDoesNotThrow(() -> helper.save(message));
        assertTrue(memoryApender.contains(logMessage, Level.INFO));
    }
}
