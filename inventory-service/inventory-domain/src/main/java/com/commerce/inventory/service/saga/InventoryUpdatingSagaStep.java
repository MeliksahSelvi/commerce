package com.commerce.inventory.service.saga;

import com.commerce.inventory.service.common.DomainComponent;
import com.commerce.inventory.service.common.saga.SagaStep;
import com.commerce.inventory.service.inventory.usecase.InventoryRequest;
import com.commerce.inventory.service.saga.helper.InventoryUpdatingHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @Author mselvi
 * @Created 11.03.2024
 */


@DomainComponent
public class InventoryUpdatingSagaStep implements SagaStep<InventoryRequest> {

    private static final Logger logger= LoggerFactory.getLogger(InventoryUpdatingSagaStep.class);

    private final InventoryUpdatingHelper inventoryUpdatingHelper;

    public InventoryUpdatingSagaStep(InventoryUpdatingHelper inventoryUpdatingHelper) {
        this.inventoryUpdatingHelper = inventoryUpdatingHelper;
    }

    @Override
    public void process(InventoryRequest inventoryRequest) {
        logger.info("Inventory updating process step started with InventoryRequest");
        inventoryUpdatingHelper.process(inventoryRequest);
    }

    @Override
    public void rollback(InventoryRequest inventoryRequest) {
        logger.info("Inventory updating rollback step started with InventoryRequest");
        inventoryUpdatingHelper.rollback(inventoryRequest);
    }
}
