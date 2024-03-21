package com.commerce.notification.service.notification.adapters.messaging;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.LoggerContext;
import com.commerce.notification.service.common.exception.NotificationDomainException;
import com.commerce.notification.service.common.valueobject.NotificationStatus;
import com.commerce.notification.service.common.valueobject.NotificationType;
import com.commerce.notification.service.notification.adapters.messaging.adapter.FakeOrderNotificationListenerHelper;
import com.commerce.notification.service.notification.adapters.messaging.appender.MemoryApender;
import com.commerce.notification.service.notification.adapters.messaging.helper.OrderNotificationListenerHelper;
import com.commerce.notification.service.notification.usecase.OrderNotificationMessage;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.LoggerFactory;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * @Author mselvi
 * @Created 21.03.2024
 */

class OrderNotificationMessageListenerAdapterTest {

    OrderNotificationMessageListenerAdapter listenerAdapter;
    MemoryApender memoryApender;

    @BeforeEach
    void setUp(){
        listenerAdapter =new OrderNotificationMessageListenerAdapter(new FakeOrderNotificationListenerHelper());

        Logger logger = (Logger) LoggerFactory.getLogger(OrderNotificationListenerHelper.class.getPackageName());
        memoryApender = new MemoryApender();
        memoryApender.setContext((LoggerContext) LoggerFactory.getILoggerFactory());
        logger.setLevel(Level.INFO);
        logger.addAppender(memoryApender);
        memoryApender.start();
    }

    @AfterEach
    void cleanUp(){
        memoryApender.reset();
        memoryApender.stop();
    }

    @Test
    void should_processMessage() {
        //given
        var message = new OrderNotificationMessage(2L, 1L, NotificationType.SHIPPING, "message");

        //when
        //then
        assertDoesNotThrow(() -> listenerAdapter.processMessage(message));
    }

    @Test
    void should_processMessage_fail_when_notification_already_send_by_order_id() {
        //given
        var message = new OrderNotificationMessage(1L, 1L, NotificationType.SHIPPING, "message");
        var logMessage = String.format("This %s notification has already sent by orderId: %d",
                notificationTypeToNotificationStatus(message.notificationType()), message.orderId());

        //when
        listenerAdapter.processMessage(message);

        //then
        assertTrue(memoryApender.contains(logMessage,Level.INFO));
    }

    private NotificationStatus notificationTypeToNotificationStatus(NotificationType notificationType) {
        return switch (notificationType) {
            case APPROVING -> NotificationStatus.APPROVED;
            case DELIVERING -> NotificationStatus.DELIVERED;
            case SHIPPING -> NotificationStatus.SHIPPED;
            case CANCELLING -> NotificationStatus.CANCELLED;
        };
    }

    @Test
    void should_processMessage_fail_when_customer_not_exist() {
        //given
        var message = new OrderNotificationMessage(2L, 2L, NotificationType.SHIPPING, "message");

        //when
        //then
        var exception = assertThrows(NotificationDomainException.class,
                () -> listenerAdapter.processMessage(message));
        assertTrue(exception.getMessage().contains("Could not find customer with"));
    }
}
