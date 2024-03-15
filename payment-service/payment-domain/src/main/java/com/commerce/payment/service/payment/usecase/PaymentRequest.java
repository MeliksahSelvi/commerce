package com.commerce.payment.service.payment.usecase;

import com.commerce.payment.service.common.model.UseCase;
import com.commerce.payment.service.common.valueobject.Money;
import com.commerce.payment.service.common.valueobject.OrderPaymentStatus;

import java.util.UUID;

/**
 * @Author mselvi
 * @Created 07.03.2024
 */

public record PaymentRequest(UUID sagaId, Long orderId, Long customerId, Money cost,
                             OrderPaymentStatus orderPaymentStatus) implements UseCase {
}
