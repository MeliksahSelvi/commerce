package com.commerce.shipping.service.shipping;

import ch.qos.logback.classic.Level;
import com.commerce.shipping.service.common.exception.ShippingNotFoundException;
import com.commerce.shipping.service.common.valueobject.Money;
import com.commerce.shipping.service.common.valueobject.NotificationType;
import com.commerce.shipping.service.common.valueobject.Quantity;
import com.commerce.shipping.service.shipping.adapters.FakeOrderNotificationShippingDataAdapter;
import com.commerce.shipping.service.shipping.adapters.messaging.OrderNotificationListenerHelper;
import com.commerce.shipping.service.shipping.common.LoggerTest;
import com.commerce.shipping.service.shipping.model.Address;
import com.commerce.shipping.service.shipping.model.OrderItem;
import com.commerce.shipping.service.shipping.usecase.OrderNotificationMessage;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @Author mselvi
 * @Created 15.04.2024
 */

class OrderNotificationListenerHelperTest extends LoggerTest<OrderNotificationListenerHelper> {

    private static final Long ALREADY_SENT_ORDER_ID = 2L;
    private static final Long ORDER_ID = 1L;

    OrderNotificationListenerHelper orderNotificationListenerHelper;

    public OrderNotificationListenerHelperTest() {
        super(OrderNotificationListenerHelper.class);
    }

    @BeforeEach
    void setUp() {
        orderNotificationListenerHelper = new OrderNotificationListenerHelper(new FakeOrderNotificationShippingDataAdapter());
    }

    @AfterEach
    protected void cleanUp() {
        cleanUpActions();
    }

    @Test
    void should_approving() {
        //given
        var message = new OrderNotificationMessage(ORDER_ID, 1L, buildAddres(), buildItems(), NotificationType.SHIPPING, "message");
        var logMessage = String.format("Shipping persisted for approving by orderId: %d", ORDER_ID);

        //when
        //then
        assertDoesNotThrow(() -> orderNotificationListenerHelper.approving(message));
        assertTrue(memoryApender.contains(logMessage, Level.INFO));
    }

    @Test
    void should_approving_fail_when_notification_already_sent() {
        //given
        var message = new OrderNotificationMessage(ALREADY_SENT_ORDER_ID, 1L, buildAddres(), buildItems(), NotificationType.SHIPPING, "message");
        var shouldExistLogMessage = String.format("This order shipping has already processed by orderId: %d", ALREADY_SENT_ORDER_ID);

        //when
        //then
        assertDoesNotThrow(() -> orderNotificationListenerHelper.approving(message));
        assertTrue(memoryApender.contains(shouldExistLogMessage, Level.INFO));
    }

    @Test
    void should_cancelling() {
        //given
        var message = new OrderNotificationMessage(ORDER_ID, 1L, buildAddres(), buildItems(), NotificationType.CANCELLING, "message");
        var logMessage = String.format("Shipping updated for cancelling by orderId: %d", ORDER_ID);

        //when
        //then
        assertDoesNotThrow(() -> orderNotificationListenerHelper.cancelling(message));
        assertTrue(memoryApender.contains(logMessage, Level.INFO));
    }

    @Test
    void should_cancelling_failed_when_shipping_not_exist_by_order_id(){
        //given
        var orderNotificationMessage = new OrderNotificationMessage(3L, 1L, buildAddres(), buildItems(), NotificationType.CANCELLING, "message");
        var errorMessage="Shipping could not find by order id: 3";

        //when
        //then
        var exception =
                assertThrows(ShippingNotFoundException.class, () -> orderNotificationListenerHelper.cancelling(orderNotificationMessage));
        assertTrue(exception.getMessage().contains(errorMessage));
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
}
