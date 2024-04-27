package com.commerce.inventory.service.common.messaging.kafka.model;

import com.commerce.inventory.service.common.valueobject.InventoryStatus;
import com.commerce.inventory.service.common.valueobject.OrderInventoryStatus;
import com.commerce.inventory.service.outbox.model.OrderOutboxPayload;

import java.util.List;
import java.util.UUID;

/**
 * @Author mselvi
 * @Created 04.04.2024
 */

public record InventoryResponseKafkaModel(String sagaId, Long orderId, Long customerId, InventoryStatus inventoryStatus,
                                          OrderInventoryStatus orderInventoryStatus,
                                          List<String> failureMessages) implements KafkaModel {

    public InventoryResponseKafkaModel(UUID sagaId, OrderOutboxPayload payload) {
        this(sagaId.toString(),payload.orderId(),payload.customerId(),payload.inventoryStatus(),payload.orderInventoryStatus(),payload.failureMessages());
    }
}
