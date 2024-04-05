package com.commerce.order.service.outbox.entity;

import com.commerce.order.service.common.outbox.OutboxPayload;
import com.commerce.order.service.common.valueobject.OrderInventoryStatus;
import com.commerce.order.service.order.entity.Order;
import com.commerce.order.service.order.entity.OrderItem;
import com.commerce.order.service.order.usecase.OrderItemPayload;

import java.math.BigDecimal;
import java.util.List;

/**
 * @Author mselvi
 * @Created 06.03.2024
 */

public record InventoryOutboxPayload(Long orderId, Long customerId, BigDecimal cost, List<OrderItemPayload> items,
                                     OrderInventoryStatus orderInventoryStatus) implements OutboxPayload {

    public InventoryOutboxPayload(Order order, OrderInventoryStatus orderInventoryStatus) {
        this(order.getId(), order.getCustomerId(), order.getCost().amount(), order.getItems().stream().map(OrderItemPayload::new).toList(), orderInventoryStatus);
    }
}
