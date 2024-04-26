package com.commerce.notification.service.notification.adapters.messaging;

import ch.qos.logback.classic.Level;
import com.commerce.notification.service.common.exception.CustomerNotFoundException;
import com.commerce.notification.service.common.valueobject.NotificationType;
import com.commerce.notification.service.notification.adapters.messaging.adapter.FakeOrderNotificationListenerHelper;
import com.commerce.notification.service.common.LoggerTest;
import com.commerce.notification.service.notification.usecase.OrderNotificationMessage;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @Author mselvi
 * @Created 21.03.2024
 */

class OrderNotificationMessageListenerAdapterTest extends LoggerTest<OrderNotificationMessageListenerAdapter> {

    OrderNotificationMessageListenerAdapter listenerAdapter;

    public OrderNotificationMessageListenerAdapterTest() {
        super(OrderNotificationMessageListenerAdapter.class);
    }

    @BeforeEach
    void setUp() {
        listenerAdapter = new OrderNotificationMessageListenerAdapter(new FakeOrderNotificationListenerHelper());
    }

    @AfterEach
    protected void cleanUp() {
        cleanUpActions();
    }

    @Test
    void should_processMessage() {
        //given
        var message = new OrderNotificationMessage(2L, 1L, NotificationType.SHIPPING, "message");

        //when
        //then
        assertDoesNotThrow(() -> listenerAdapter.processMessage(message));
        assertTrue(memoryApender.contains("OrderNotificationMessage processing action is started", Level.INFO));
    }

    @Test
    void should_processMessage_fail_when_customer_not_exist() {
        //given
        var message = new OrderNotificationMessage(2L, 2L, NotificationType.SHIPPING, "message");

        //when
        //then
        var exception = assertThrows(CustomerNotFoundException.class,
                () -> listenerAdapter.processMessage(message));
        assertTrue(exception.getMessage().contains("Could not find customer with id: 2"));
    }
}
