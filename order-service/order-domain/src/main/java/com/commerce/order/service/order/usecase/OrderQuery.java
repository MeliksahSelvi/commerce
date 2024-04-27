package com.commerce.order.service.order.usecase;

import com.commerce.order.service.common.model.UseCase;
import com.commerce.order.service.common.valueobject.OrderStatus;

/**
 * @Author mselvi
 * @Created 27.04.2024
 */

public record OrderQuery(Long id, OrderStatus orderStatus) implements UseCase {
}
