package com.commerce.order.service.common.messaging.kafka.model;

import com.commerce.order.service.common.valueobject.OrderPaymentStatus;
import com.commerce.order.service.outbox.entity.PaymentOutboxPayload;

import java.math.BigDecimal;

/**
 * @Author mselvi
 * @Created 04.04.2024
 */

public record PaymentRequestKafkaModel(String sagaId, Long orderId, Long customerId, BigDecimal cost,
                                       OrderPaymentStatus orderPaymentStatus) implements KafkaModel {

    public PaymentRequestKafkaModel(PaymentOutboxPayload payload) {
        this(payload.sagaId().toString(), payload.orderId(), payload.customerId(), payload.cost(), payload.orderPaymentStatus());
    }
}
