package com.commerce.order.service.saga;

import com.commerce.order.service.common.DomainComponent;
import com.commerce.order.service.common.saga.SagaStep;
import com.commerce.order.service.order.usecase.InventoryResponse;
import com.commerce.order.service.saga.helper.InventoryUpdatingRollbackHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @Author mselvi
 * @Created 18.03.2024
 */

@DomainComponent
public class InventoryUpdatingRollbackSagaStep implements SagaStep<InventoryResponse> {

    private static final Logger logger= LoggerFactory.getLogger(InventoryUpdatingRollbackSagaStep.class);
    private final InventoryUpdatingRollbackHelper inventoryUpdatingRollbackHelper;

    public InventoryUpdatingRollbackSagaStep(InventoryUpdatingRollbackHelper inventoryUpdatingRollbackHelper) {
        this.inventoryUpdatingRollbackHelper = inventoryUpdatingRollbackHelper;
    }

    @Override
    public void process(InventoryResponse inventoryResponse) {
        logger.info("Processing action for inventory updating rollback started with InventoryResponse");
        inventoryUpdatingRollbackHelper.process(inventoryResponse);
    }

    @Override
    public void rollback(InventoryResponse inventoryResponse) {
        logger.info("Rollback action for inventory updating rollback started with InventoryResponse");
        inventoryUpdatingRollbackHelper.rollback(inventoryResponse);
    }
}
