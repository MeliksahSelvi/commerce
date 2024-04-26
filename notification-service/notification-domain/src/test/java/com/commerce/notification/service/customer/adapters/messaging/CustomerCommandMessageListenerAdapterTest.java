package com.commerce.notification.service.customer.adapters.messaging;

import ch.qos.logback.classic.Level;
import com.commerce.notification.service.common.LoggerTest;
import com.commerce.notification.service.customer.adapters.messaging.adapter.FakeCustomerCommandListenerHelper;
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

class CustomerCommandMessageListenerAdapterTest extends LoggerTest<CustomerCommandMessageListenerAdapter> {

    CustomerCommandMessageListenerAdapter listenerAdapter;

    public CustomerCommandMessageListenerAdapterTest() {
        super(CustomerCommandMessageListenerAdapter.class);
    }

    @BeforeEach
    void setUp() {
        listenerAdapter = new CustomerCommandMessageListenerAdapter(new FakeCustomerCommandListenerHelper());
    }

    @AfterEach
    protected void cleanUp() {
        cleanUpActions();
    }

    @Test
    void should_processMessage() {
        //given
        var message = new CustomerInfo(1L, "name", "surname", "email@gmail.com");
        var logMessage = "CustomerInfo save action started by email: email@gmail.com";

        //when
        //then
        assertDoesNotThrow(() -> listenerAdapter.processMessage(message));
        assertTrue(memoryApender.contains(logMessage, Level.INFO));
    }
}
