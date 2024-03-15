package com.commerce.order.service.order.usecase;

import com.commerce.order.service.common.model.UseCase;

/**
 * @Author mselvi
 * @Created 05.03.2024
 */

public record TrackOrder(Long orderId) implements UseCase {
}
