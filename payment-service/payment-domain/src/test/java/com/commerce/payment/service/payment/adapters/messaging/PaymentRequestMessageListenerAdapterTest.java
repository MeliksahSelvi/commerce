package com.commerce.payment.service.payment.adapters.messaging;

import ch.qos.logback.classic.Level;
import com.commerce.payment.service.adapter.FakePaymentRequestListenerHelper;
import com.commerce.payment.service.common.LoggerTest;
import com.commerce.payment.service.common.valueobject.Money;
import com.commerce.payment.service.common.valueobject.OrderPaymentStatus;
import com.commerce.payment.service.payment.adapters.messaging.PaymentRequestMessageListenerAdapter;
import com.commerce.payment.service.payment.usecase.PaymentRequest;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * @Author mselvi
 * @Created 21.03.2024
 */

class PaymentRequestMessageListenerAdapterTest extends LoggerTest<PaymentRequestMessageListenerAdapter> {

    PaymentRequestMessageListenerAdapter adapter;

    public PaymentRequestMessageListenerAdapterTest() {
        super(PaymentRequestMessageListenerAdapter.class);
    }

    @BeforeEach
    void setUp() {
        adapter = new PaymentRequestMessageListenerAdapter(new FakePaymentRequestListenerHelper());
    }

    @AfterEach
    @Override
    protected void cleanUp() {
        cleanUpActions();
    }

    @Test
    void should_completePayment() {
        //given
        var paymentRequest = new PaymentRequest(UUID.randomUUID(), 1L, 1L, new Money(BigDecimal.valueOf(17)), OrderPaymentStatus.PENDING);
        var logMessage = "Payment complete action started with PaymentRequest";

        //when
        //then
        assertDoesNotThrow(() -> adapter.completePayment(paymentRequest));
        assertTrue(memoryApender.contains(logMessage, Level.INFO));
    }

    @Test
    void should_cancelPayment() {
        //given
        var paymentRequest = new PaymentRequest(UUID.randomUUID(), 1L, 1L, new Money(BigDecimal.valueOf(17)), OrderPaymentStatus.CANCELLED);
        var logMessage = "Payment cancel action started with PaymentRequest";

        //when
        //then
        assertDoesNotThrow(() -> adapter.cancelPayment(paymentRequest));
        assertTrue(memoryApender.contains(logMessage, Level.INFO));
    }
}
