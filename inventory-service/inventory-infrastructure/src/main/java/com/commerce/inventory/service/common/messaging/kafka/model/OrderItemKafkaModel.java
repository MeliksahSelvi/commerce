package com.commerce.inventory.service.common.messaging.kafka.model;

import com.commerce.inventory.service.inventory.usecase.OrderItem;

import java.math.BigDecimal;

/**
 * @Author mselvi
 * @Created 04.04.2024
 */

public record OrderItemKafkaModel(Long id, Long orderId, Long productId, int quantity, BigDecimal price,
                                  BigDecimal totalPrice) implements KafkaModel {

    public OrderItemKafkaModel(OrderItem orderItem) {
        this(orderItem.id(), orderItem.orderId(), orderItem.productId(), orderItem.quantity().value(),
                orderItem.price().amount(), orderItem.totalPrice().amount());
    }
}
