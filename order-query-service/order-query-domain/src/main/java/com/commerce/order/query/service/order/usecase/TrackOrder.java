package com.commerce.order.query.service.order.usecase;

import com.commerce.order.query.service.common.model.UseCase;

/**
 * @Author mselvi
 * @Created 27.04.2024
 */

public record TrackOrder(Long orderId) implements UseCase {
}