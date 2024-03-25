package com.commerce.order.service.saga;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.LoggerContext;
import com.commerce.order.service.adapter.FakePaymentResponseHelper;
import com.commerce.order.service.appender.MemoryApender;
import com.commerce.order.service.common.valueobject.Money;
import com.commerce.order.service.common.valueobject.PaymentStatus;
import com.commerce.order.service.order.usecase.PaymentResponse;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * @Author mselvi
 * @Created 21.03.2024
 */

class PaymentResponseSagaStepTest {

    private static final UUID sagaId=UUID.fromString("5bf96862-0c98-41ef-a952-e03d2ded6a6a");


    PaymentResponseSagaStep paymentResponseSagaStep;
    MemoryApender memoryApender;

    @BeforeEach
    void setUp() {
        paymentResponseSagaStep = new PaymentResponseSagaStep(new FakePaymentResponseHelper());

        Logger logger = (Logger) LoggerFactory.getLogger(paymentResponseSagaStep.getClass());
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
    void should_process(){
        //given
        var paymentResponse = buildPaymentResponse();
        var logMessage="Processing action for payment started with PaymentResponse";

        //when
        //then
        assertDoesNotThrow(() -> paymentResponseSagaStep.process(paymentResponse));
        assertTrue(memoryApender.contains(logMessage, Level.INFO));
    }

    @Test
    void should_rollback(){
        //given
        var inventoryResponse = buildPaymentResponse();
        var logMessage="Rollback action for payment started with PaymentResponse";

        //when
        //then
        assertDoesNotThrow(() -> paymentResponseSagaStep.rollback(inventoryResponse));
        assertTrue(memoryApender.contains(logMessage, Level.INFO));
    }

    private PaymentResponse buildPaymentResponse(){
        return new PaymentResponse(sagaId,1L,1L,1L,new Money(BigDecimal.TEN), PaymentStatus.COMPLETED,new ArrayList<>());
    }
}
