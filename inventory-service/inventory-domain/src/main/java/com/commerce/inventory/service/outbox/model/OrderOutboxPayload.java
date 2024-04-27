package com.commerce.inventory.service.outbox.model;

import com.commerce.inventory.service.common.outbox.OutboxPayload;
import com.commerce.inventory.service.common.valueobject.InventoryStatus;
import com.commerce.inventory.service.common.valueobject.OrderInventoryStatus;
import com.commerce.inventory.service.inventory.usecase.InventoryRequest;

import java.util.List;

/**
 * @Author mselvi
 * @Created 07.03.2024
 */

public record OrderOutboxPayload(Long orderId, Long customerId, OrderInventoryStatus orderInventoryStatus,
                                 InventoryStatus inventoryStatus,
                                 List<String> failureMessages) implements OutboxPayload {

    public OrderOutboxPayload(InventoryRequest inventoryRequest, InventoryStatus inventoryStatus, List<String> failureMessages) {
        this(inventoryRequest.orderId(), inventoryRequest.customerId(), inventoryRequest.orderInventoryStatus(), inventoryStatus, failureMessages);
    }
}
