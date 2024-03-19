package com.commerce.order.service.order.adapters.messaging;

import com.commerce.order.service.common.DomainComponent;
import com.commerce.order.service.common.saga.SagaStep;
import com.commerce.order.service.order.port.messaging.input.InventoryCheckingResponseMessageListener;
import com.commerce.order.service.order.usecase.InventoryResponse;
import org.springframework.beans.factory.annotation.Qualifier;

/**
 * @Author mselvi
 * @Created 18.03.2024
 */

@DomainComponent
public class InventoryCheckingResponseMessageListenerAdapter implements InventoryCheckingResponseMessageListener {

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
            case AVAILABLE -> inventoryCheckingSagaStep.process(inventoryResponse);
            case NON_AVAILABLE -> inventoryCheckingSagaStep.rollback(inventoryResponse);
        }
    }

    @Override
    public void checkingRollback(InventoryResponse inventoryResponse) {
        switch (inventoryResponse.inventoryStatus()) {
            case AVAILABLE -> inventoryCheckingRollbackSagaStep.process(inventoryResponse);
            case NON_AVAILABLE -> inventoryCheckingRollbackSagaStep.rollback(inventoryResponse);
        }
    }
}
