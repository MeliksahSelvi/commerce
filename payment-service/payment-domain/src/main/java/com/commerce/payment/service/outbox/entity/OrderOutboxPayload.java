package com.commerce.payment.service.outbox.entity;

import com.commerce.payment.service.common.outbox.OutboxPayload;
import com.commerce.payment.service.common.valueobject.PaymentStatus;
import com.commerce.payment.service.payment.entity.Payment;

import java.math.BigDecimal;
import java.util.List;

/**
 * @Author mselvi
 * @Created 07.03.2024
 */

public record OrderOutboxPayload(Long paymentId, Long orderId, Long customerId, BigDecimal cost,
                                 PaymentStatus paymentStatus,
                                 List<String> failureMessages) implements OutboxPayload {

    public OrderOutboxPayload(Payment payment, List<String> failureMessages) {
        this(payment.getId(), payment.getOrderId(), payment.getCustomerId(),
                payment.getCost() == null ? null : payment.getCost().amount(), payment.getPaymentStatus(), failureMessages);
    }
}
