package com.commerce.order.service.order.port.messaging.input;

import com.commerce.order.service.order.usecase.InventoryResponse;

/**
 * @Author mselvi
 * @Created 18.03.2024
 */

public interface InventoryUpdatingResponseMessageListener {

    void updating(InventoryResponse inventoryResponse);

    void updatingRollback(InventoryResponse inventoryResponse);
}
