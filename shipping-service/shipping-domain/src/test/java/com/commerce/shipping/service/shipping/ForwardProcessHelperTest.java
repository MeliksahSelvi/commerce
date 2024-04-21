package com.commerce.shipping.service.shipping;

import ch.qos.logback.classic.Level;
import com.commerce.shipping.service.common.exception.ShippingNotFoundException;
import com.commerce.shipping.service.common.valueobject.DeliveryStatus;
import com.commerce.shipping.service.shipping.adapters.FakeForwardShippingDataAdapter;
import com.commerce.shipping.service.shipping.adapters.FakeOrderNotificationMessagePublisherAdapter;
import com.commerce.shipping.service.shipping.common.LoggerTest;
import com.commerce.shipping.service.shipping.handler.helper.ForwardProcessHelper;
import com.commerce.shipping.service.shipping.usecase.ForwardProcess;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @Author mselvi
 * @Created 15.04.2024
 */

public class ForwardProcessHelperTest extends LoggerTest<ForwardProcessHelper> {

    ForwardProcessHelper forwardProcessHelper;

    public ForwardProcessHelperTest() {
        super(ForwardProcessHelper.class);
    }

    @BeforeEach
    void setUp() {
        forwardProcessHelper = new ForwardProcessHelper(new FakeOrderNotificationMessagePublisherAdapter(), new FakeForwardShippingDataAdapter());
    }

    @AfterEach
    protected void cleanUp() {
        cleanUpActions();
    }

    @Test
    void should_forward() {
        //given
        var forwardProcess = new ForwardProcess(1L, DeliveryStatus.APPROVED, DeliveryStatus.SHIPPED);
        var logMessage = "Notification has sent to notification service by delivery status: SHIPPED";

        //when
        //then
        var shipping = assertDoesNotThrow(() -> forwardProcessHelper.forward(forwardProcess));
        assertTrue(memoryApender.contains(logMessage, Level.INFO));
        assertEquals(forwardProcess.newStatus(), shipping.getDeliveryStatus());
    }

    @Test
    void should_forward_fail_when_shipping_not_exist() {
        //given
        var forwardProcess = new ForwardProcess(2L, DeliveryStatus.APPROVED, DeliveryStatus.SHIPPED);
        var errorMessage = "Shipping could not found by orderId: 2";

        //when
        //then
        var exception = assertThrows(ShippingNotFoundException.class, () -> forwardProcessHelper.forward(forwardProcess));
        assertTrue(exception.getMessage().contains(errorMessage));
    }
}
