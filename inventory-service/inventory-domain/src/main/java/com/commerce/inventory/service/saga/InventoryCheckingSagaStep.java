package com.commerce.inventory.service.saga;

import com.commerce.inventory.service.common.DomainComponent;
import com.commerce.inventory.service.common.saga.SagaStep;
import com.commerce.inventory.service.inventory.usecase.InventoryRequest;
import com.commerce.inventory.service.saga.helper.InventoryCheckingHelper;

/**
 * @Author mselvi
 * @Created 11.03.2024
 */

@DomainComponent
public class InventoryCheckingSagaStep implements SagaStep<InventoryRequest> {

    private final InventoryCheckingHelper inventoryCheckingHelper;

    public InventoryCheckingSagaStep(InventoryCheckingHelper inventoryCheckingHelper) {
        this.inventoryCheckingHelper = inventoryCheckingHelper;
    }

    @Override
    public void process(InventoryRequest inventoryRequest) {
        inventoryCheckingHelper.process(inventoryRequest);
    }

    @Override
    public void rollback(InventoryRequest inventoryRequest) {
        inventoryCheckingHelper.rollback(inventoryRequest);
    }
}