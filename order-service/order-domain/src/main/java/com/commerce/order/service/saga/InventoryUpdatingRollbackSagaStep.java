package com.commerce.order.service.saga;

import com.commerce.order.service.common.DomainComponent;
import com.commerce.order.service.common.saga.SagaStep;
import com.commerce.order.service.order.usecase.InventoryResponse;
import com.commerce.order.service.saga.helper.InventoryUpdatingRollbackHelper;

/**
 * @Author mselvi
 * @Created 18.03.2024
 */

@DomainComponent
public class InventoryUpdatingRollbackSagaStep implements SagaStep<InventoryResponse> {

    private final InventoryUpdatingRollbackHelper inventoryUpdatingRollbackHelper;

    public InventoryUpdatingRollbackSagaStep(InventoryUpdatingRollbackHelper inventoryUpdatingRollbackHelper) {
        this.inventoryUpdatingRollbackHelper = inventoryUpdatingRollbackHelper;
    }

    @Override
    public void process(InventoryResponse inventoryResponse) {
        inventoryUpdatingRollbackHelper.process(inventoryResponse);
    }

    @Override
    public void rollback(InventoryResponse inventoryResponse) {
        inventoryUpdatingRollbackHelper.rollback(inventoryResponse);
    }
}
