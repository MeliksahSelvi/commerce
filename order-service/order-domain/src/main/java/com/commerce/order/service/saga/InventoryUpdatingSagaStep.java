package com.commerce.order.service.saga;

import com.commerce.order.service.common.DomainComponent;
import com.commerce.order.service.common.saga.SagaStep;
import com.commerce.order.service.order.usecase.InventoryResponse;
import com.commerce.order.service.saga.helper.InventoryUpdatingHelper;


/**
 * @Author mselvi
 * @Created 05.03.2024
 */

@DomainComponent
public class InventoryUpdatingSagaStep implements SagaStep<InventoryResponse> {

    private final InventoryUpdatingHelper inventoryUpdatingHelper;

    public InventoryUpdatingSagaStep(InventoryUpdatingHelper inventoryUpdatingHelper) {
        this.inventoryUpdatingHelper = inventoryUpdatingHelper;
    }

    @Override
    public void process(InventoryResponse useCase) {
        inventoryUpdatingHelper.process(useCase);
    }

    @Override
    public void rollback(InventoryResponse useCase) {
        inventoryUpdatingHelper.rollback(useCase);
    }
}
