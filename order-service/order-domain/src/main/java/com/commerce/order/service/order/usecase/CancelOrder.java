package com.commerce.order.service.order.usecase;

import com.commerce.order.service.common.model.UseCase;

/**
 * @Author mselvi
 * @Created 11.03.2024
 */

public record CancelOrder(Long orderId) implements UseCase {
}
