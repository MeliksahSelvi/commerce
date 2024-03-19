package com.commerce.order.service.saga;

import com.commerce.order.service.common.DomainComponent;
import com.commerce.order.service.common.saga.SagaStep;
import com.commerce.order.service.order.usecase.InventoryResponse;
import com.commerce.order.service.saga.helper.InventoryCheckingRollbackHelper;

/**
 * @Author mselvi
 * @Created 18.03.2024
 */

@DomainComponent
public class InventoryCheckingRollbackSagaStep implements SagaStep<InventoryResponse> {

    private final InventoryCheckingRollbackHelper inventoryCheckingRollbackHelper;

    public InventoryCheckingRollbackSagaStep(InventoryCheckingRollbackHelper inventoryCheckingRollbackHelper) {
        this.inventoryCheckingRollbackHelper = inventoryCheckingRollbackHelper;
    }

    @Override
    public void process(InventoryResponse inventoryResponse) {
        inventoryCheckingRollbackHelper.process(inventoryResponse);
    }

    @Override
    public void rollback(InventoryResponse inventoryResponse) {
        inventoryCheckingRollbackHelper.rollback(inventoryResponse);
    }
}
