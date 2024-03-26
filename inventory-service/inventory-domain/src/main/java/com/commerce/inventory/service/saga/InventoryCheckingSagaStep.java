package com.commerce.inventory.service.saga;

import com.commerce.inventory.service.common.DomainComponent;
import com.commerce.inventory.service.common.saga.SagaStep;
import com.commerce.inventory.service.inventory.usecase.InventoryRequest;
import com.commerce.inventory.service.saga.helper.InventoryCheckingHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @Author mselvi
 * @Created 11.03.2024
 */

@DomainComponent
public class InventoryCheckingSagaStep implements SagaStep<InventoryRequest> {

    private static final Logger logger = LoggerFactory.getLogger(InventoryCheckingSagaStep.class);

    private final InventoryCheckingHelper inventoryCheckingHelper;

    public InventoryCheckingSagaStep(InventoryCheckingHelper inventoryCheckingHelper) {
        this.inventoryCheckingHelper = inventoryCheckingHelper;
    }

    @Override
    public void process(InventoryRequest inventoryRequest) {
        logger.info("Inventory checking process saga step action started with InventoryRequest");
        inventoryCheckingHelper.process(inventoryRequest);
    }

    @Override
    public void rollback(InventoryRequest inventoryRequest) {
        logger.info("Inventory checking rollback saga step action started with InventoryRequest");
        inventoryCheckingHelper.rollback(inventoryRequest);
    }
}
