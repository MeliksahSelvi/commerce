package com.commerce.order.service.order.usecase;

import com.commerce.order.service.common.model.UseCase;
import com.commerce.order.service.common.valueobject.Money;
import com.commerce.order.service.common.valueobject.PaymentStatus;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

/**
 * @Author mselvi
 * @Created 05.03.2024
 */

public record PaymentResponse(UUID sagaId, Long orderId, Long paymentId, Long customerId, Money price,
                              PaymentStatus paymentStatus, List<String> failureMessages) implements UseCase {

}
