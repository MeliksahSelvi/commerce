package com.commerce.order.service.saga;

import com.commerce.order.service.common.DomainComponent;
import com.commerce.order.service.common.saga.SagaStep;
import com.commerce.order.service.order.usecase.InventoryResponse;
import com.commerce.order.service.saga.helper.InventoryUpdatingHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * @Author mselvi
 * @Created 05.03.2024
 */

@DomainComponent
public class InventoryUpdatingSagaStep implements SagaStep<InventoryResponse> {

    private static final Logger logger= LoggerFactory.getLogger(InventoryUpdatingSagaStep.class);
    private final InventoryUpdatingHelper inventoryUpdatingHelper;

    public InventoryUpdatingSagaStep(InventoryUpdatingHelper inventoryUpdatingHelper) {
        this.inventoryUpdatingHelper = inventoryUpdatingHelper;
    }

    @Override
    public void process(InventoryResponse useCase) {
        logger.info("Processing action for inventory updating started with InventoryResponse");
        inventoryUpdatingHelper.process(useCase);
    }

    @Override
    public void rollback(InventoryResponse useCase) {
        logger.info("Rollback action for inventory updating started with InventoryResponse");
        inventoryUpdatingHelper.rollback(useCase);
    }
}
