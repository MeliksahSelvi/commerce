package com.commerce.order.service.order.adapters.messaging;

import com.commerce.order.service.common.DomainComponent;
import com.commerce.order.service.common.saga.SagaStep;
import com.commerce.order.service.order.port.messaging.input.InventoryCheckingResponseMessageListener;
import com.commerce.order.service.order.usecase.InventoryResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;

/**
 * @Author mselvi
 * @Created 18.03.2024
 */

@DomainComponent
public class InventoryCheckingResponseMessageListenerAdapter implements InventoryCheckingResponseMessageListener {

    private static final Logger logger = LoggerFactory.getLogger(InventoryCheckingResponseMessageListenerAdapter.class);

    @Qualifier("inventoryCheckingSagaStep")
    private final SagaStep<InventoryResponse> inventoryCheckingSagaStep;

    @Qualifier("inventoryCheckingRollbackSagaStep")
    private final SagaStep<InventoryResponse> inventoryCheckingRollbackSagaStep;

    public InventoryCheckingResponseMessageListenerAdapter(SagaStep<InventoryResponse> inventoryCheckingSagaStep,
                                                           SagaStep<InventoryResponse> inventoryCheckingRollbackSagaStep) {
        this.inventoryCheckingSagaStep = inventoryCheckingSagaStep;
        this.inventoryCheckingRollbackSagaStep = inventoryCheckingRollbackSagaStep;
    }

    @Override
    public void checking(InventoryResponse inventoryResponse) {
        switch (inventoryResponse.inventoryStatus()) {
            case AVAILABLE -> {
                logger.info("InventoryResponse is available for Inventory checking action");
                inventoryCheckingSagaStep.process(inventoryResponse);
            }
            case NON_AVAILABLE -> {
                logger.info("InventoryResponse is not available for Inventory checking action");
                inventoryCheckingSagaStep.rollback(inventoryResponse);
            }
        }
    }

    @Override
    public void checkingRollback(InventoryResponse inventoryResponse) {
        switch (inventoryResponse.inventoryStatus()) {
            case AVAILABLE -> {
                logger.info("InventoryResponse is available for Inventory checking rollback action");
                inventoryCheckingRollbackSagaStep.process(inventoryResponse);
            }
            case NON_AVAILABLE -> {
                logger.info("InventoryResponse is not available for Inventory checking rollback action");
                inventoryCheckingRollbackSagaStep.rollback(inventoryResponse);
            }
        }
    }
}
