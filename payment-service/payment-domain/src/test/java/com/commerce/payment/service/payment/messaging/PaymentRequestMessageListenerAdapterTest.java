package com.commerce.payment.service.payment.messaging;

import ch.qos.logback.classic.Level;
import com.commerce.payment.service.common.exception.AccountNotFoundException;
import com.commerce.payment.service.payment.adapter.FakePaymentRequestListenerHelper;
import com.commerce.payment.service.common.LoggerTest;
import com.commerce.payment.service.common.valueobject.Money;
import com.commerce.payment.service.common.valueobject.OrderPaymentStatus;
import com.commerce.payment.service.payment.adapters.messaging.PaymentRequestMessageListenerAdapter;
import com.commerce.payment.service.payment.usecase.PaymentRequest;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

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
    void should_completePayment_fail_when_account_not_found() {
        //given
        var paymentRequest = new PaymentRequest(UUID.randomUUID(), 1L, 2L, new Money(BigDecimal.valueOf(17)), OrderPaymentStatus.PENDING);
        var logMessage = "Payment complete action started with PaymentRequest";

        //when
        //then
        var exception = assertThrows(AccountNotFoundException.class,
                () -> adapter.completePayment(paymentRequest));
        assertTrue(exception.getMessage().contains("Could not find Account"));
        assertTrue(memoryApender.contains(logMessage, Level.INFO));
    }

    @Test
    void should_completePayment_fail_when_currentBalance_not_enough_for_order_cost() {
        //given
        var paymentRequest = new PaymentRequest(UUID.randomUUID(), 1L, 1L, new Money(BigDecimal.valueOf(101)), OrderPaymentStatus.PENDING);
        var logMessage = "Payment complete action started with PaymentRequest";

        //when
        var failureMessages = adapter.completePayment(paymentRequest);

        //then
        assertTrue(failureMessagesIncludeThatMessage(failureMessages, "doesn't have enough balance for payment"));
        assertTrue(memoryApender.contains(logMessage, Level.INFO));
    }

    @Test
    void should_completePayment_fail_when_order_cost_is_null() {
        //given
        var paymentRequest = new PaymentRequest(UUID.randomUUID(), 1L, 1L, null, OrderPaymentStatus.PENDING);
        var logMessage = "Payment complete action started with PaymentRequest";

        //when
        var failureMessages = adapter.completePayment(paymentRequest);

        //then
        assertTrue(failureMessagesIncludeThatMessage(failureMessages, "Cost can't be zero and must be greater than zero!"));
        assertTrue(memoryApender.contains(logMessage, Level.INFO));
    }

    @Test
    void should_completePayment_fail_when_order_cost_is_not_greater_than_zero() {
        //given
        var paymentRequest = new PaymentRequest(UUID.randomUUID(), 1L, 1L, new Money(BigDecimal.ZERO), OrderPaymentStatus.PENDING);
        var logMessage = "Payment complete action started with PaymentRequest";

        //when
        var failureMessages = adapter.completePayment(paymentRequest);

        //then
        assertTrue(failureMessagesIncludeThatMessage(failureMessages, "Cost can't be zero and must be greater than zero!"));
        assertTrue(memoryApender.contains(logMessage, Level.INFO));
    }

    @Test
    void should_completePayment_fail_when_account_new_currentBalance_is_not_greater_than_zero() {
        //given
        var paymentRequest = new PaymentRequest(UUID.randomUUID(), 1L, 1L, new Money(BigDecimal.valueOf(101)), OrderPaymentStatus.PENDING);
        var logMessage = "Payment complete action started with PaymentRequest";

        //when
        var failureMessages = adapter.completePayment(paymentRequest);

        //then
        assertTrue(failureMessagesIncludeThatMessage(failureMessages,"doesn't have enough balance for payment"));
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

    @Test
    void should_cancelPayment_fail_when_order_cost_is_null() {
        //given
        var paymentRequest = new PaymentRequest(UUID.randomUUID(), 1L, 1L, null, OrderPaymentStatus.CANCELLED);
        var logMessage = "Payment cancel action started with PaymentRequest";

        //when
        var failureMessages = adapter.cancelPayment(paymentRequest);
        assertTrue(memoryApender.contains(logMessage, Level.INFO));

        //then
        assertTrue(failureMessagesIncludeThatMessage(failureMessages, "Cost can't be zero and must be greater than zero!"));
    }

    @Test
    void should_cancelPayment_fail_when_order_cost_is_not_greater_than_zero() {
        //given
        var paymentRequest = new PaymentRequest(UUID.randomUUID(), 1L, 1L, new Money(BigDecimal.ZERO), OrderPaymentStatus.CANCELLED);
        var logMessage = "Payment cancel action started with PaymentRequest";

        //when
        var failureMessages = adapter.cancelPayment(paymentRequest);

        //then
        assertTrue(failureMessagesIncludeThatMessage(failureMessages, "Cost can't be zero and must be greater than zero!"));
        assertTrue(memoryApender.contains(logMessage, Level.INFO));
    }

    @Test
    void should_cancelPayment_fail_when_account_not_found() {
        //given
        var paymentRequest = new PaymentRequest(UUID.randomUUID(), 1L, 2L, new Money(BigDecimal.valueOf(17)), OrderPaymentStatus.PENDING);
        var logMessage = "Payment cancel action started with PaymentRequest";

        //when
        //then
        var exception = assertThrows(AccountNotFoundException.class,
                () -> adapter.cancelPayment(paymentRequest));
        assertTrue(exception.getMessage().contains("Could not find Account"));
        assertTrue(memoryApender.contains(logMessage, Level.INFO));
    }

    private boolean failureMessagesIncludeThatMessage(List<String> failureMessages, String thatMessage) {
        return failureMessages.stream().anyMatch(failureMessage -> failureMessage.contains(thatMessage));
    }
}
