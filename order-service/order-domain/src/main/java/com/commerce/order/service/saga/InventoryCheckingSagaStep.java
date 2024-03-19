package com.commerce.order.service.saga;

import com.commerce.order.service.common.DomainComponent;
import com.commerce.order.service.common.saga.SagaStep;
import com.commerce.order.service.order.usecase.InventoryResponse;
import com.commerce.order.service.saga.helper.InventoryCheckingHelper;


/**
 * @Author mselvi
 * @Created 04.03.2024
 */

@DomainComponent
public class InventoryCheckingSagaStep implements SagaStep<InventoryResponse> {

    private final InventoryCheckingHelper inventoryCheckingHelper;

    public InventoryCheckingSagaStep(InventoryCheckingHelper inventoryCheckingHelper) {
        this.inventoryCheckingHelper = inventoryCheckingHelper;
    }

    @Override
    public void process(InventoryResponse useCase) {
        inventoryCheckingHelper.process(useCase);
    }

    @Override
    public void rollback(InventoryResponse useCase) {
        inventoryCheckingHelper.rollback(useCase);
    }
}
