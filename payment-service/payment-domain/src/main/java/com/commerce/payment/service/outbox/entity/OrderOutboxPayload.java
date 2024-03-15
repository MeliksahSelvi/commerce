package com.commerce.payment.service.outbox.entity;

import com.commerce.payment.service.common.valueobject.PaymentStatus;

import java.math.BigDecimal;
import java.util.List;

/**
 * @Author mselvi
 * @Created 07.03.2024
 */

public record OrderOutboxPayload(Long paymentId, Long orderId, Long customerId, BigDecimal cost,
                                 PaymentStatus paymentStatus,
                                 List<String> failureMessages) {
}
