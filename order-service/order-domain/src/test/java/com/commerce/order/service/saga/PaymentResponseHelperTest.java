package com.commerce.order.service.saga;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.LoggerContext;
import com.commerce.order.service.adapter.*;
import com.commerce.order.service.appender.MemoryApender;
import com.commerce.order.service.common.exception.InventoryOutboxNotFoundException;
import com.commerce.order.service.common.exception.OrderNotFoundException;
import com.commerce.order.service.common.exception.PaymentOutboxNotFoundException;
import com.commerce.order.service.common.valueobject.Money;
import com.commerce.order.service.common.valueobject.PaymentStatus;
import com.commerce.order.service.order.usecase.PaymentResponse;
import com.commerce.order.service.saga.helper.PaymentResponseHelper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * @Author mselvi
 * @Created 21.03.2024
 */

class PaymentResponseHelperTest {

    private static final UUID sagaId = UUID.fromString("5bf96862-0c98-41ef-a952-e03d2ded6a6a");
    private static final UUID wrongSagaId = UUID.fromString("5bf96862-0c98-41ef-a952-e03d2d");

    PaymentResponseHelper paymentResponseHelper;
    MemoryApender memoryApender;

    @BeforeEach
    void setUp() {
        paymentResponseHelper = new PaymentResponseHelper(new FakeInventoryOutboxDataAdapter(), new FakePaymentOutboxDataAdapter(),
                new FakePendingOrderDataAdapter(), new FakeSagaHelper(), new FakeJsonAdapter());

        Logger logger = (Logger) LoggerFactory.getLogger(paymentResponseHelper.getClass());
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
        var paymentResponse = buildPaymentResponseWithParameters(sagaId,1L);
        var logMessage=String.format("InventoryOutbox persisted for payment response with sagaId: %s",paymentResponse.sagaId());

        //when
        //then
        assertDoesNotThrow(() -> paymentResponseHelper.process(paymentResponse));
        assertTrue(memoryApender.contains(logMessage, Level.INFO));
    }

    @Test
    void should_process_fail_when_order_not_exist() {
        //given
        var paymentResponse = buildPaymentResponseWithParameters(sagaId,2L);

        //when
        //then
        var orderNotFoundException = assertThrows(OrderNotFoundException.class, () -> paymentResponseHelper.process(paymentResponse));
        assertEquals(String.format("Order could not found with id: %d", paymentResponse.orderId()), orderNotFoundException.getMessage());
    }

    @Test
    void should_process_fail_when_saga_id_wrong() {
        //given
        var paymentResponse = buildPaymentResponseWithParameters(wrongSagaId,1L);

        //when
        //then
        var exception =
                assertThrows(PaymentOutboxNotFoundException.class, () -> paymentResponseHelper.process(paymentResponse));
        assertEquals(String.format("PaymentOutbox could not found with sagaId: %s", wrongSagaId), exception.getMessage());
    }

    @Test
    void should_rollback(){
        //given
        var paymentResponse = buildPaymentResponseWithParameters(sagaId,1L);
        var logMessage=String.format("InventoryOutbox persisted for payment rollback with sagaId: %s",paymentResponse.sagaId());

        //when
        //then
        assertDoesNotThrow(() -> paymentResponseHelper.rollback(paymentResponse));
        assertTrue(memoryApender.contains(logMessage, Level.INFO));
    }

    @Test
    void should_rollback_fail_when_order_not_exist() {
        //given
        var paymentResponse = buildPaymentResponseWithParameters(sagaId,2L);

        //when
        //then
        var orderNotFoundException = assertThrows(OrderNotFoundException.class,
                () -> paymentResponseHelper.rollback(paymentResponse));
        assertEquals(String.format("Order could not found with id: %d", paymentResponse.orderId()), orderNotFoundException.getMessage());
    }

    @Test
    void should_rollback_fail_when_saga_id_wrong() {
        //given
        var paymentResponse = buildPaymentResponseWithParameters(wrongSagaId,1L);

        //when
        //then
        var exception =
                assertThrows(PaymentOutboxNotFoundException.class, () -> paymentResponseHelper.rollback(paymentResponse));
        assertEquals(String.format("PaymentOutbox could not found with sagaId: %s", wrongSagaId), exception.getMessage());
    }

    private PaymentResponse buildPaymentResponseWithParameters(UUID sagaId, Long orderId){
        return new PaymentResponse(sagaId,orderId,1L,1L,new Money(BigDecimal.TEN), PaymentStatus.COMPLETED,new ArrayList<>());
    }
}
