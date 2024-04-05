package com.commerce.payment.service.common.messaging.kafka.model;

import com.commerce.payment.service.common.valueobject.OrderPaymentStatus;

import java.math.BigDecimal;

/**
 * @Author mselvi
 * @Created 04.04.2024
 */

public record PaymentRequestKafkaModel(String sagaId, Long orderId, Long customerId, BigDecimal cost,
                                       OrderPaymentStatus orderPaymentStatus) implements KafkaModel {

}
