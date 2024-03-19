package com.commerce.inventory.service.outbox.entity;

import com.commerce.inventory.service.common.outbox.OutboxPayload;
import com.commerce.inventory.service.common.valueobject.InventoryStatus;
import com.commerce.inventory.service.common.valueobject.OrderInventoryStatus;

import java.util.List;

/**
 * @Author mselvi
 * @Created 07.03.2024
 */

public record OrderOutboxPayload(Long orderId, Long customerId, OrderInventoryStatus orderInventoryStatus,
                                 InventoryStatus inventoryStatus, List<String> failureMessages) implements OutboxPayload {
}
