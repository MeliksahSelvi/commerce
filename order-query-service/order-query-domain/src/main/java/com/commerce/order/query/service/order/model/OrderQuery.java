package com.commerce.order.query.service.order.model;

import com.commerce.order.query.service.common.valueobject.OrderStatus;

/**
 * @Author mselvi
 * @Created 27.04.2024
 */

public record OrderQuery(Long id, OrderStatus orderStatus) {
}
