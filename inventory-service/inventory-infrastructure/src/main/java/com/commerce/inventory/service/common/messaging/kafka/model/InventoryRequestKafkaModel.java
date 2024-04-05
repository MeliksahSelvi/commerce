package com.commerce.inventory.service.common.messaging.kafka.model;

import com.commerce.inventory.service.common.valueobject.OrderInventoryStatus;

import java.math.BigDecimal;
import java.util.List;

/**
 * @Author mselvi
 * @Created 04.04.2024
 */

public record InventoryRequestKafkaModel(String sagaId, Long orderId, Long customerId, BigDecimal cost,
                                         OrderInventoryStatus orderInventoryStatus,
                                         List<OrderItemKafkaModel> items) implements KafkaModel {
}
