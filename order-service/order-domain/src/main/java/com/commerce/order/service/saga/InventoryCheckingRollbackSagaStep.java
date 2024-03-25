package com.commerce.order.service.saga;

import com.commerce.order.service.common.DomainComponent;
import com.commerce.order.service.common.saga.SagaStep;
import com.commerce.order.service.order.usecase.InventoryResponse;
import com.commerce.order.service.saga.helper.InventoryCheckingRollbackHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @Author mselvi
 * @Created 18.03.2024
 */

@DomainComponent
public class InventoryCheckingRollbackSagaStep implements SagaStep<InventoryResponse> {

    private static final Logger logger= LoggerFactory.getLogger(InventoryCheckingRollbackSagaStep.class);
    private final InventoryCheckingRollbackHelper inventoryCheckingRollbackHelper;

    public InventoryCheckingRollbackSagaStep(InventoryCheckingRollbackHelper inventoryCheckingRollbackHelper) {
        this.inventoryCheckingRollbackHelper = inventoryCheckingRollbackHelper;
    }

    @Override
    public void process(InventoryResponse inventoryResponse) {
        logger.info("Processing action for inventory checking rollback started with InventoryResponse");
        inventoryCheckingRollbackHelper.process(inventoryResponse);
    }

    @Override
    public void rollback(InventoryResponse inventoryResponse) {
        logger.info("Rollback action for inventory checking rollback started with InventoryResponse");
        inventoryCheckingRollbackHelper.rollback(inventoryResponse);
    }
}
