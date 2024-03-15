package com.commerce.order.service.order.port.messaging.input;

import com.commerce.order.service.order.usecase.InventoryResponse;

/**
 * @Author mselvi
 * @Created 07.03.2024
 */

public interface InventoryResponseMessageListener {

    void checking(InventoryResponse inventoryResponse);

    void processing(InventoryResponse inventoryResponse);
}
