package com.commerce.inventory.service.inventory.port.messaging.input;

import com.commerce.inventory.service.inventory.usecase.InventoryRequest;

/**
 * @Author mselvi
 * @Created 07.03.2024
 */

public interface InventoryRequestMessageListener {

    void checking(InventoryRequest inventoryRequest);

    void processing(InventoryRequest inventoryRequest);

}
