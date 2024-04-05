package com.commerce.order.service.common.messaging.kafka.model;

import com.commerce.order.service.common.valueobject.PaymentStatus;

import java.math.BigDecimal;
import java.util.List;

/**
 * @Author mselvi
 * @Created 04.04.2024
 */

public record PaymentResponseKafkaModel(String sagaId, Long paymentId, Long orderId, Long customerId,
                                        BigDecimal cost, PaymentStatus paymentStatus,
                                        List<String> failureMessages) implements KafkaModel {
}
