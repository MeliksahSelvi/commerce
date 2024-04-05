package com.commerce.order.service.common.messaging.kafka.model;

import com.commerce.order.service.common.valueobject.InventoryStatus;
import com.commerce.order.service.common.valueobject.OrderInventoryStatus;

import java.util.List;

/**
 * @Author mselvi
 * @Created 04.04.2024
 */

public record InventoryResponseKafkaModel(String sagaId, Long orderId, Long customerId, InventoryStatus inventoryStatus,
                                          OrderInventoryStatus orderInventoryStatus,
                                          List<String> failureMessages) implements KafkaModel {
}
