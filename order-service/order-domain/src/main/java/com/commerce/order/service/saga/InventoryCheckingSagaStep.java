package com.commerce.order.service.saga;

import com.commerce.order.service.common.DomainComponent;
import com.commerce.order.service.common.saga.SagaStep;
import com.commerce.order.service.order.usecase.InventoryResponse;
import com.commerce.order.service.saga.helper.InventoryCheckingHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * @Author mselvi
 * @Created 04.03.2024
 */

@DomainComponent
public class InventoryCheckingSagaStep implements SagaStep<InventoryResponse> {

    private static final Logger logger= LoggerFactory.getLogger(InventoryCheckingSagaStep.class);
    private final InventoryCheckingHelper inventoryCheckingHelper;

    public InventoryCheckingSagaStep(InventoryCheckingHelper inventoryCheckingHelper) {
        this.inventoryCheckingHelper = inventoryCheckingHelper;
    }

    @Override
    public void process(InventoryResponse useCase) {
        logger.info("InventoryResponse processing action is started");
        inventoryCheckingHelper.process(useCase);
    }

    @Override
    public void rollback(InventoryResponse useCase) {
        logger.info("InventoryResponse rollback action is started");
        inventoryCheckingHelper.rollback(useCase);
    }
}
