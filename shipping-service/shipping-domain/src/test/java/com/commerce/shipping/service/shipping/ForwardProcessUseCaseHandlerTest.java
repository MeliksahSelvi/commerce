package com.commerce.shipping.service.shipping;

import ch.qos.logback.classic.Level;
import com.commerce.shipping.service.common.exception.ShippingNotFoundException;
import com.commerce.shipping.service.common.valueobject.DeliveryStatus;
import com.commerce.shipping.service.shipping.adapters.FakeForwardShippingDataAdapter;
import com.commerce.shipping.service.shipping.adapters.FakeOrderNotificationMessagePublisherAdapter;
import com.commerce.shipping.service.shipping.common.LoggerTest;
import com.commerce.shipping.service.shipping.entity.Shipping;
import com.commerce.shipping.service.shipping.handler.ForwardProcessUseCaseHandler;
import com.commerce.shipping.service.shipping.usecase.ForwardProcess;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @Author mselvi
 * @Created 21.03.2024
 */

class ForwardProcessUseCaseHandlerTest extends LoggerTest<ForwardProcessUseCaseHandler> {

    ForwardProcessUseCaseHandler forwardProcessUseCaseHandler;

    public ForwardProcessUseCaseHandlerTest() {
        super(ForwardProcessUseCaseHandler.class);
    }

    @BeforeEach
    void setUp() {
        forwardProcessUseCaseHandler = new ForwardProcessUseCaseHandler(new FakeOrderNotificationMessagePublisherAdapter(), new FakeForwardShippingDataAdapter());
    }

    @AfterEach
    void cleanUp() {
        destroy();
    }

    @Test
    void should_forward() {
        //given
        var forwardProcess = new ForwardProcess(1L, DeliveryStatus.APPROVED, DeliveryStatus.SHIPPED);
        var logMessage = String.format("Notification has sent to notification service by delivery status: %s", forwardProcess.newStatus());

        //when
        //then
        Shipping shipping = assertDoesNotThrow(() -> forwardProcessUseCaseHandler.handle(forwardProcess));
        assertTrue(memoryApender.contains(logMessage, Level.INFO));
        assertEquals(forwardProcess.newStatus(), shipping.getDeliveryStatus());
    }

    @Test
    void should_forward_fail_when_shipping_not_exist() {
        //given
        var forwardProcess = new ForwardProcess(2L, DeliveryStatus.APPROVED, DeliveryStatus.SHIPPED);

        //when
        //then
        var exception = assertThrows(ShippingNotFoundException.class, () -> forwardProcessUseCaseHandler.handle(forwardProcess));
        assertTrue(exception.getMessage().contains("Shipping could not found"));
    }

}
