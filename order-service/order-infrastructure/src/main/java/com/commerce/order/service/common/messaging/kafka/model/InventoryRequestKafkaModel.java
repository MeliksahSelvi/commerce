package com.commerce.order.service.common.messaging.kafka.model;

import com.commerce.order.service.common.valueobject.OrderInventoryStatus;
import com.commerce.order.service.outbox.entity.InventoryOutboxPayload;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

/**
 * @Author mselvi
 * @Created 04.04.2024
 */

public record InventoryRequestKafkaModel(String sagaId, Long orderId, Long customerId, BigDecimal cost,
                                         OrderInventoryStatus orderInventoryStatus,
                                         List<OrderItemKafkaModel> items) implements KafkaModel {

    public InventoryRequestKafkaModel(UUID sagaId, InventoryOutboxPayload payload) {
        this(sagaId.toString(), payload.orderId(), payload.customerId(), payload.cost(), payload.orderInventoryStatus(),
                payload.items().stream().map(OrderItemKafkaModel::new).toList());
    }
}
