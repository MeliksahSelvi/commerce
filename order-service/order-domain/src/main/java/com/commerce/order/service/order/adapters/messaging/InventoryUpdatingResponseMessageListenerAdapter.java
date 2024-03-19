package com.commerce.order.service.order.adapters.messaging;

import com.commerce.order.service.common.DomainComponent;
import com.commerce.order.service.common.saga.SagaStep;
import com.commerce.order.service.order.port.messaging.input.InventoryUpdatingResponseMessageListener;
import com.commerce.order.service.order.usecase.InventoryResponse;
import org.springframework.beans.factory.annotation.Qualifier;

/**
 * @Author mselvi
 * @Created 18.03.2024
 */

@DomainComponent
public class InventoryUpdatingResponseMessageListenerAdapter implements InventoryUpdatingResponseMessageListener {

    @Qualifier("inventoryUpdatingSagaStep")
    private final SagaStep<InventoryResponse> inventoryProcessingSagaStep;

    @Qualifier("inventoryUpdatingRollbackSagaStep")
    private final SagaStep<InventoryResponse> inventoryProcessingRollbackSagaStep;

    public InventoryUpdatingResponseMessageListenerAdapter(SagaStep<InventoryResponse> inventoryProcessingSagaStep,
                                                           SagaStep<InventoryResponse> inventoryProcessingRollbackSagaStep) {
        this.inventoryProcessingSagaStep = inventoryProcessingSagaStep;
        this.inventoryProcessingRollbackSagaStep = inventoryProcessingRollbackSagaStep;
    }

    @Override
    public void processing(InventoryResponse inventoryResponse) {
        switch (inventoryResponse.inventoryStatus()) {
            case AVAILABLE -> inventoryProcessingSagaStep.process(inventoryResponse);
            case NON_AVAILABLE -> inventoryProcessingSagaStep.rollback(inventoryResponse);
        }
    }

    @Override
    public void processingRollback(InventoryResponse inventoryResponse) {
        switch (inventoryResponse.inventoryStatus()) {
            case AVAILABLE -> inventoryProcessingRollbackSagaStep.process(inventoryResponse);
            case NON_AVAILABLE -> inventoryProcessingRollbackSagaStep.rollback(inventoryResponse);
        }
    }
}
