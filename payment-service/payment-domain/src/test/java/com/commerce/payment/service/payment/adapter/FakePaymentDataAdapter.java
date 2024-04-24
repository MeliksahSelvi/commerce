package com.commerce.payment.service.payment.adapter;

import com.commerce.payment.service.common.valueobject.Money;
import com.commerce.payment.service.common.valueobject.PaymentStatus;
import com.commerce.payment.service.payment.entity.Payment;
import com.commerce.payment.service.payment.port.jpa.PaymentDataPort;

import java.math.BigDecimal;
import java.util.Optional;

/**
 * @Author mselvi
 * @Created 21.03.2024
 */

public class FakePaymentDataAdapter implements PaymentDataPort {

    private static final Long EXIST_ORDER_ID = 1L;

    @Override
    public Payment save(Payment payment) {
        return Payment.builder()
                .id(payment.getId())
                .orderId(payment.getOrderId())
                .customerId(payment.getCustomerId())
                .cost(payment.getCost())
                .paymentstatus(payment.getPaymentStatus())
                .build();
    }

    @Override
    public Optional<Payment> findByOrderId(Long orderId) {
        if (EXIST_ORDER_ID != orderId) {
            return Optional.empty();
        }
        return Optional.of(
                Payment.builder()
                        .id(1L)
                        .orderId(orderId)
                        .customerId(1L)
                        .paymentstatus(PaymentStatus.COMPLETED)
                        .cost(new Money(BigDecimal.valueOf(17)))
                        .build()
        );
    }
}
