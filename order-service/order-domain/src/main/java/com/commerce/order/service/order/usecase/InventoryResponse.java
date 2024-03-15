package com.commerce.order.service.order.usecase;

import com.commerce.order.service.common.model.UseCase;
import com.commerce.order.service.common.valueobject.InventoryStatus;
import com.commerce.order.service.common.valueobject.OrderInventoryStatus;

import java.util.List;
import java.util.UUID;

/**
 * @Author mselvi
 * @Created 07.03.2024
 */

public record InventoryResponse(UUID sagaId, Long orderId, Long customerId, InventoryStatus inventoryStatus,
                                OrderInventoryStatus orderInventoryStatus, List<String> failureMessages) implements UseCase {
}
