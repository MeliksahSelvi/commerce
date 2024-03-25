package com.commerce.order.service.order.adapters.messaging;

import com.commerce.order.service.common.DomainComponent;
import com.commerce.order.service.common.saga.SagaStep;
import com.commerce.order.service.order.port.messaging.input.InventoryUpdatingResponseMessageListener;
import com.commerce.order.service.order.usecase.InventoryResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;

/**
 * @Author mselvi
 * @Created 18.03.2024
 */

@DomainComponent
public class InventoryUpdatingResponseMessageListenerAdapter implements InventoryUpdatingResponseMessageListener {

    private static final Logger logger = LoggerFactory.getLogger(InventoryUpdatingResponseMessageListenerAdapter.class);

    @Qualifier("inventoryUpdatingSagaStep")
    private final SagaStep<InventoryResponse> inventoryUpdatingSagaStep;

    @Qualifier("inventoryUpdatingRollbackSagaStep")
    private final SagaStep<InventoryResponse> inventoryUpdatingRollbackSagaStep;

    public InventoryUpdatingResponseMessageListenerAdapter(SagaStep<InventoryResponse> inventoryUpdatingSagaStep,
                                                           SagaStep<InventoryResponse> inventoryUpdatingRollbackSagaStep) {
        this.inventoryUpdatingSagaStep = inventoryUpdatingSagaStep;
        this.inventoryUpdatingRollbackSagaStep = inventoryUpdatingRollbackSagaStep;
    }

    @Override
    public void updating(InventoryResponse inventoryResponse) {
        switch (inventoryResponse.inventoryStatus()) {
            case AVAILABLE -> {
                logger.info("InventoryResponse is available for Inventory updating action");
                inventoryUpdatingSagaStep.process(inventoryResponse);
            }
            case NON_AVAILABLE -> {
                logger.info("InventoryResponse is not available for Inventory updating action");
                inventoryUpdatingSagaStep.rollback(inventoryResponse);
            }
        }
    }

    @Override
    public void updatingRollback(InventoryResponse inventoryResponse) {
        switch (inventoryResponse.inventoryStatus()) {
            case AVAILABLE -> {
                logger.info("InventoryResponse is available for Inventory updating rollback action");
                inventoryUpdatingRollbackSagaStep.process(inventoryResponse);
            }
            case NON_AVAILABLE -> {
                logger.info("InventoryResponse is not available for Inventory updating rollback action");
                inventoryUpdatingRollbackSagaStep.rollback(inventoryResponse);
            }
        }
    }
}
