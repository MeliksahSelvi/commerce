package com.commerce.inventory.service.inventory.usecase;

import com.commerce.inventory.service.common.model.UseCase;
import com.commerce.inventory.service.common.valueobject.Money;
import com.commerce.inventory.service.common.valueobject.OrderInventoryStatus;

import java.util.List;
import java.util.UUID;

/**
 * @Author mselvi
 * @Created 07.03.2024
 */

public record InventoryRequest(UUID sagaId, Long orderId, Long customerId, Money cost,
                               OrderInventoryStatus orderInventoryStatus,
                               List<OrderItem> items) implements UseCase {
}
