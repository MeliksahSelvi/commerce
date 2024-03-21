package com.commerce.shipping.service.shipping;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.LoggerContext;
import com.commerce.shipping.service.common.valueobject.Money;
import com.commerce.shipping.service.common.valueobject.NotificationType;
import com.commerce.shipping.service.common.valueobject.Quantity;
import com.commerce.shipping.service.shipping.adapters.FakeForwardShippingDataAdapter;
import com.commerce.shipping.service.shipping.adapters.FakeOrderNotificationShippingDataAdapter;
import com.commerce.shipping.service.shipping.adapters.messaging.OrderNotificationMessageListenerAdapter;
import com.commerce.shipping.service.shipping.appender.MemoryApender;
import com.commerce.shipping.service.shipping.entity.Address;
import com.commerce.shipping.service.shipping.entity.OrderItem;
import com.commerce.shipping.service.shipping.usecase.OrderNotificationMessage;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * @Author mselvi
 * @Created 21.03.2024
 */

class OrderNotificationMessageListenerAdapterTest {

    private static final Long ALREADY_SENT_ORDER_ID=2L;
    private static final Long ORDER_ID=1L;

    OrderNotificationMessageListenerAdapter orderNotificationMessageListenerAdapter;
    MemoryApender memoryApender;

    @BeforeEach
    void setUp() {
        orderNotificationMessageListenerAdapter = new OrderNotificationMessageListenerAdapter(new FakeOrderNotificationShippingDataAdapter());
        Logger logger = (Logger) LoggerFactory.getLogger(orderNotificationMessageListenerAdapter.getClass().getPackageName());
        memoryApender = new MemoryApender();
        memoryApender.setContext((LoggerContext) LoggerFactory.getILoggerFactory());
        logger.setLevel(Level.INFO);
        logger.addAppender(memoryApender);
        memoryApender.start();
    }

    @AfterEach
    void cleanUp() {
        memoryApender.reset();
        memoryApender.stop();
    }

    @Test
    void should_approving() {
        //given
        var message = new OrderNotificationMessage(ORDER_ID, 1L, buildAddres(), buildItems(), NotificationType.SHIPPING, "message");
        var shouldExistLogMessage=String.format("Shipping persisted for approving by orderId: %d", ORDER_ID);
        var shouldNotExistLogMessage=String.format("This order shipping has already processed by orderId: {}", ORDER_ID);

        //when
        orderNotificationMessageListenerAdapter.approving(message);

        //then
        assertTrue(memoryApender.contains(shouldExistLogMessage,Level.INFO));
        assertFalse(memoryApender.contains(shouldNotExistLogMessage,Level.INFO));
    }

    @Test
    void should_approving_fail_when_notification_already_sent(){
        //given
        var message = new OrderNotificationMessage(ALREADY_SENT_ORDER_ID, 1L, buildAddres(), buildItems(), NotificationType.SHIPPING, "message");
        var shouldExistLogMessage=String.format("This order shipping has already processed by orderId: %d", ALREADY_SENT_ORDER_ID);

        //when
        orderNotificationMessageListenerAdapter.approving(message);

        //then
        assertTrue(memoryApender.contains(shouldExistLogMessage,Level.INFO));
    }

    private Address buildAddres() {
        return Address.builder()
                .id(1L)
                .city("city")
                .county("county")
                .neighborhood("neighborhood")
                .street("street")
                .postalCode("postalCode")
                .build();
    }

    private List<OrderItem> buildItems() {
        return List.of(
                OrderItem.builder()
                        .id(1L)
                        .orderId(ORDER_ID)
                        .productId(1L)
                        .totalPrice(new Money(BigDecimal.TEN))
                        .price(new Money(BigDecimal.ONE))
                        .quantity(new Quantity(10))
                        .build(),
                OrderItem.builder()
                        .id(2L)
                        .orderId(ORDER_ID)
                        .productId(2L)
                        .totalPrice(new Money(BigDecimal.ONE))
                        .price(new Money(BigDecimal.ONE))
                        .quantity(new Quantity(1))
                        .build(),
                OrderItem.builder()
                        .id(3L)
                        .orderId(ORDER_ID)
                        .productId(3L)
                        .totalPrice(new Money(BigDecimal.valueOf(6)))
                        .price(new Money(BigDecimal.valueOf(2)))
                        .quantity(new Quantity(3))
                        .build()
        );
    }

    @Test
    void should_cancelling() {

    }
}
