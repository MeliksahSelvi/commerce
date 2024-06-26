package com.commerce.shipping.service.shipping;

import ch.qos.logback.classic.Level;
import com.commerce.shipping.service.common.exception.ShippingNotFoundException;
import com.commerce.shipping.service.common.valueobject.DeliveryStatus;
import com.commerce.shipping.service.shipping.adapters.FakeForwardProcessHelper;
import com.commerce.shipping.service.shipping.common.LoggerTest;
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
        forwardProcessUseCaseHandler = new ForwardProcessUseCaseHandler(new FakeForwardProcessHelper());
    }

    @AfterEach
    protected void cleanUp() {
        cleanUpActions();
    }

    @Test
    void should_forward() {
        //given
        var forwardProcess = new ForwardProcess(1L, DeliveryStatus.APPROVED, DeliveryStatus.SHIPPED);
        var logMessage = "Forward process action started by orderId: 1";

        //when
        //then
        var shipping = assertDoesNotThrow(() -> forwardProcessUseCaseHandler.handle(forwardProcess));
        assertTrue(memoryApender.contains(logMessage, Level.INFO));
    }

    @Test
    void should_forward_fail_when_shipping_not_exist() {
        //given
        var forwardProcess = new ForwardProcess(2L, DeliveryStatus.APPROVED, DeliveryStatus.SHIPPED);
        var errorMessage = "Shipping could not found by orderId: 2";

        //when
        //then
        var exception = assertThrows(ShippingNotFoundException.class, () -> forwardProcessUseCaseHandler.handle(forwardProcess));
        assertTrue(exception.getMessage().contains(errorMessage));
    }

}
