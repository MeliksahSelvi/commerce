package com.commerce.payment.service.payment.messaging;

import com.commerce.payment.service.payment.adapter.FakePaymentRequestListenerHelper;
import com.commerce.payment.service.common.exception.AccountNotFoundException;
import com.commerce.payment.service.common.valueobject.Money;
import com.commerce.payment.service.common.valueobject.OrderPaymentStatus;
import com.commerce.payment.service.payment.adapters.messaging.helper.PaymentRequestListenerHelper;
import com.commerce.payment.service.payment.usecase.PaymentRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * @Author mselvi
 * @Created 21.03.2024
 */

class PaymentRequestListenerHelperTest {

    PaymentRequestListenerHelper paymentRequestListenerHelper;

    @BeforeEach
    void setUp() {
        paymentRequestListenerHelper = new FakePaymentRequestListenerHelper();
    }

    @Test
    void should_completePayment() {
        //given
        var paymentRequest = new PaymentRequest(UUID.randomUUID(), 1L, 1L, new Money(BigDecimal.valueOf(17)), OrderPaymentStatus.PENDING);

        //when
        var failureMessages = paymentRequestListenerHelper.completePayment(paymentRequest);

        //then
        assertTrue(failureMessages.isEmpty());
    }

    @Test
    void should_completePayment_fail_when_account_not_found() {
        //given
        var paymentRequest = new PaymentRequest(UUID.randomUUID(), 1L, 2L, new Money(BigDecimal.valueOf(17)), OrderPaymentStatus.PENDING);

        //when
        //then
        var exception = assertThrows(AccountNotFoundException.class,
                () -> paymentRequestListenerHelper.completePayment(paymentRequest));
        assertTrue(exception.getMessage().contains("Could not find Account"));
    }

    @Test
    void should_completePayment_fail_when_currentBalance_not_enough_for_order_cost() {
        //given
        var paymentRequest = new PaymentRequest(UUID.randomUUID(), 1L, 1L, new Money(BigDecimal.valueOf(101)), OrderPaymentStatus.PENDING);

        //when
        var failureMessages = paymentRequestListenerHelper.completePayment(paymentRequest);

        //then
        assertTrue(failureMessagesIncludeThatMessage(failureMessages, "doesn't have enough balance for payment"));
    }

    @Test
    void should_completePayment_fail_when_order_cost_is_null() {
        //given
        var paymentRequest = new PaymentRequest(UUID.randomUUID(), 1L, 1L, null, OrderPaymentStatus.PENDING);

        //when
        var failureMessages = paymentRequestListenerHelper.completePayment(paymentRequest);

        //then
        assertTrue(failureMessagesIncludeThatMessage(failureMessages, "Cost can't be zero and must be greater than zero!"));
    }

    @Test
    void should_completePayment_fail_when_order_cost_is_not_greater_than_zero() {
        //given
        var paymentRequest = new PaymentRequest(UUID.randomUUID(), 1L, 1L, new Money(BigDecimal.ZERO), OrderPaymentStatus.PENDING);

        //when
        var failureMessages = paymentRequestListenerHelper.completePayment(paymentRequest);

        //then
        assertTrue(failureMessagesIncludeThatMessage(failureMessages, "Cost can't be zero and must be greater than zero!"));
    }

    @Test
    void should_completePayment_fail_when_account_new_currentBalance_is_not_greater_than_zero() {
        //given
        var paymentRequest = new PaymentRequest(UUID.randomUUID(), 1L, 1L, new Money(BigDecimal.valueOf(101)), OrderPaymentStatus.PENDING);

        //when
        var failureMessages = paymentRequestListenerHelper.completePayment(paymentRequest);

        //then
        assertTrue(failureMessagesIncludeThatMessage(failureMessages,"doesn't have enough balance for payment"));
    }

    @Test
    void should_cancelPayment() {
        //given
        var paymentRequest = new PaymentRequest(UUID.randomUUID(), 1L, 1L, new Money(BigDecimal.valueOf(17)), OrderPaymentStatus.CANCELLED);

        //when
        var failureMessages = paymentRequestListenerHelper.cancelPayment(paymentRequest);

        //then
        assertTrue(failureMessages.isEmpty());
    }

    @Test
    void should_cancelPayment_fail_when_order_cost_is_null() {
        //given
        var paymentRequest = new PaymentRequest(UUID.randomUUID(), 1L, 1L, null, OrderPaymentStatus.CANCELLED);

        //when
        var failureMessages = paymentRequestListenerHelper.cancelPayment(paymentRequest);

        //then
        assertTrue(failureMessagesIncludeThatMessage(failureMessages, "Cost can't be zero and must be greater than zero!"));
    }

    @Test
    void should_cancelPayment_fail_when_order_cost_is_not_greater_than_zero() {
        //given
        var paymentRequest = new PaymentRequest(UUID.randomUUID(), 1L, 1L, new Money(BigDecimal.ZERO), OrderPaymentStatus.CANCELLED);

        //when
        var failureMessages = paymentRequestListenerHelper.cancelPayment(paymentRequest);

        //then
        assertTrue(failureMessagesIncludeThatMessage(failureMessages, "Cost can't be zero and must be greater than zero!"));
    }

    @Test
    void should_cancelPayment_fail_when_account_not_found() {
        //given
        var paymentRequest = new PaymentRequest(UUID.randomUUID(), 1L, 2L, new Money(BigDecimal.valueOf(17)), OrderPaymentStatus.PENDING);

        //when
        //then
        var exception = assertThrows(AccountNotFoundException.class,
                () -> paymentRequestListenerHelper.cancelPayment(paymentRequest));
        assertTrue(exception.getMessage().contains("Could not find Account"));
    }

    private boolean failureMessagesIncludeThatMessage(List<String> failureMessages, String thatMessage) {
        return failureMessages.stream().anyMatch(failureMessage -> failureMessage.contains(thatMessage));
    }
}
