package com.commerce.payment.service.common.messaging.kafka.model;

import com.commerce.payment.service.common.valueobject.PaymentStatus;
import com.commerce.payment.service.outbox.model.OrderOutboxPayload;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

/**
 * @Author mselvi
 * @Created 04.04.2024
 */

public record PaymentResponseKafkaModel(String sagaId, Long paymentId, Long orderId, Long customerId,
                                        BigDecimal cost, PaymentStatus paymentStatus,
                                        List<String> failureMessages) implements KafkaModel {

    public PaymentResponseKafkaModel(UUID sagaId, OrderOutboxPayload payload) {
        this(sagaId.toString(), payload.paymentId(), payload.orderId(), payload.customerId(), payload.cost(), payload.paymentStatus(), payload.failureMessages());
    }
}
