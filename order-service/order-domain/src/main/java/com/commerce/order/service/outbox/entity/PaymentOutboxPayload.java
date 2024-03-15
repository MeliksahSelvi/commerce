package com.commerce.order.service.outbox.entity;

import com.commerce.order.service.common.valueobject.OrderPaymentStatus;
import com.commerce.order.service.order.entity.Order;

import java.math.BigDecimal;
import java.util.UUID;

/**
 * @Author mselvi
 * @Created 06.03.2024
 */

public record PaymentOutboxPayload(UUID sagaId, Long orderId, Long customerId, BigDecimal cost,
                                   OrderPaymentStatus orderPaymentStatus) {

    public PaymentOutboxPayload(UUID sagaId, Order order, OrderPaymentStatus paymentStatus) {
        this(sagaId, order.getId(), order.getCustomerId(), order.getCost().amount(), paymentStatus);
    }
}
