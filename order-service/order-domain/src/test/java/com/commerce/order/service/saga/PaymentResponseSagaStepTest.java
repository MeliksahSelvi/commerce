package com.commerce.order.service.saga;

import ch.qos.logback.classic.Level;
import com.commerce.order.service.adapter.helper.FakePaymentResponseHelper;
import com.commerce.order.service.common.LoggerTest;
import com.commerce.order.service.common.valueobject.Money;
import com.commerce.order.service.common.valueobject.PaymentStatus;
import com.commerce.order.service.order.usecase.PaymentResponse;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * @Author mselvi
 * @Created 21.03.2024
 */

class PaymentResponseSagaStepTest extends LoggerTest<PaymentResponseSagaStep> {

    private static final UUID sagaId = UUID.fromString("5bf96862-0c98-41ef-a952-e03d2ded6a6a");

    PaymentResponseSagaStep paymentResponseSagaStep;

    public PaymentResponseSagaStepTest() {
        super(PaymentResponseSagaStep.class);
    }

    @BeforeEach
    void setUp() {
        paymentResponseSagaStep = new PaymentResponseSagaStep(new FakePaymentResponseHelper());
    }

    @AfterEach
    void cleanUp() {
        destroy();
    }

    @Test
    void should_process() {
        //given
        var paymentResponse = buildPaymentResponse(2L);
        var logMessage = "Processing action for payment started with PaymentResponse";

        //when
        //then
        assertDoesNotThrow(() -> paymentResponseSagaStep.process(paymentResponse));
        assertTrue(memoryApender.contains(logMessage, Level.INFO));
    }

    @Test
    void should_rollback() {
        //given
        var inventoryResponse = buildPaymentResponse(2L);
        var logMessage = "Rollback action for payment started with PaymentResponse";

        //when
        //then
        assertDoesNotThrow(() -> paymentResponseSagaStep.rollback(inventoryResponse));
        assertTrue(memoryApender.contains(logMessage, Level.INFO));
    }

    private PaymentResponse buildPaymentResponse(Long orderId) {
        return new PaymentResponse(sagaId, orderId, 1L, 1L, new Money(BigDecimal.TEN), PaymentStatus.COMPLETED, new ArrayList<>());
    }
}
