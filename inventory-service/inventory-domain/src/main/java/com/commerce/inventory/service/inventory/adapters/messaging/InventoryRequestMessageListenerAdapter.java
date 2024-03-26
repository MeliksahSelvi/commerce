package com.commerce.inventory.service.inventory.adapters.messaging;

import com.commerce.inventory.service.common.DomainComponent;
import com.commerce.inventory.service.common.saga.SagaStep;
import com.commerce.inventory.service.inventory.port.messaging.input.InventoryRequestMessageListener;
import com.commerce.inventory.service.inventory.usecase.InventoryRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;

/**
 * @Author mselvi
 * @Created 07.03.2024
 */

@DomainComponent
public class InventoryRequestMessageListenerAdapter implements InventoryRequestMessageListener {

    private static final Logger logger= LoggerFactory.getLogger(InventoryRequestMessageListenerAdapter.class);

    @Qualifier("inventoryUpdatingSagaStep")
    private final SagaStep<InventoryRequest> inventoryUpdatingSagaStep;

    @Qualifier("inventoryCheckingSagaStep")
    private final SagaStep<InventoryRequest> inventoryCheckingSagaStep;

    public InventoryRequestMessageListenerAdapter(SagaStep<InventoryRequest> inventoryUpdatingSagaStep, SagaStep<InventoryRequest> inventoryCheckingSagaStep) {
        this.inventoryUpdatingSagaStep = inventoryUpdatingSagaStep;
        this.inventoryCheckingSagaStep = inventoryCheckingSagaStep;
    }


    @Override
    public void checking(InventoryRequest inventoryRequest) {
        switch (inventoryRequest.orderInventoryStatus()) {
            case CHECKING -> {
                logger.info("Process action for inventory checking started with InventoryRequest");
                inventoryCheckingSagaStep.process(inventoryRequest);
            }
            case CHECKING_ROLLBACK -> {
                logger.info("Rollback action for inventory checking started with InventoryRequest");
                inventoryCheckingSagaStep.rollback(inventoryRequest);
            }
        }
    }


    @Override
    public void updating(InventoryRequest inventoryRequest) {
        switch (inventoryRequest.orderInventoryStatus()) {
            case UPDATING -> {
                logger.info("Process action for inventory updating started with InventoryRequest");
                inventoryUpdatingSagaStep.process(inventoryRequest);
            }
            case UPDATING_ROLLBACK -> {
                logger.info("Rollback action for inventory updating started with InventoryRequest");
                inventoryUpdatingSagaStep.rollback(inventoryRequest);
            }
        }
    }
}
