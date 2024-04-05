package com.commerce.order.service.common.messaging.kafka.model;

import com.commerce.order.service.order.entity.OrderItem;
import com.commerce.order.service.order.usecase.OrderItemPayload;

import java.math.BigDecimal;

/**
 * @Author mselvi
 * @Created 04.04.2024
 */

public record OrderItemKafkaModel(Long id, Long orderId, Long productId, int quantity, BigDecimal price,
                                  BigDecimal totalPrice) implements KafkaModel {

    public OrderItemKafkaModel(OrderItem orderItem) {
        this(orderItem.getId(), orderItem.getOrderId(), orderItem.getProductId(), orderItem.getQuantity().value(),
                orderItem.getPrice().amount(), orderItem.getTotalPrice().amount());
    }

    public OrderItemKafkaModel(OrderItemPayload payload) {
        this(payload.getId(), payload.getOrderId(), payload.getProductId(), payload.getQuantity(),
                payload.getPrice(), payload.getTotalPrice());
    }
}
