package com.commerce.inventory.service.saga;

import com.commerce.inventory.service.common.DomainComponent;
import com.commerce.inventory.service.common.saga.SagaStep;
import com.commerce.inventory.service.inventory.usecase.InventoryRequest;
import com.commerce.inventory.service.saga.helper.InventoryUpdatingHelper;

/**
 * @Author mselvi
 * @Created 11.03.2024
 */


@DomainComponent
public class InventoryUpdatingSagaStep implements SagaStep<InventoryRequest> {

    private final InventoryUpdatingHelper inventoryUpdatingHelper;

    public InventoryUpdatingSagaStep(InventoryUpdatingHelper inventoryUpdatingHelper) {
        this.inventoryUpdatingHelper = inventoryUpdatingHelper;
    }

    @Override
    public void process(InventoryRequest inventoryRequest) {
        inventoryUpdatingHelper.process(inventoryRequest);
    }

    @Override
    public void rollback(InventoryRequest inventoryRequest) {
        inventoryUpdatingHelper.rollback(inventoryRequest);
    }
}
