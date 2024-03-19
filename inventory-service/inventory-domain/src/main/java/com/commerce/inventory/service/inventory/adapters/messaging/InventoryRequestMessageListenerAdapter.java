package com.commerce.inventory.service.inventory.adapters.messaging;

import com.commerce.inventory.service.common.DomainComponent;
import com.commerce.inventory.service.common.saga.SagaStep;
import com.commerce.inventory.service.inventory.port.messaging.input.InventoryRequestMessageListener;
import com.commerce.inventory.service.inventory.usecase.InventoryRequest;
import org.springframework.beans.factory.annotation.Qualifier;

/**
 * @Author mselvi
 * @Created 07.03.2024
 */

@DomainComponent
public class InventoryRequestMessageListenerAdapter implements InventoryRequestMessageListener {

    @Qualifier("inventoryProcessingSagaStep")
    private final SagaStep<InventoryRequest> inventoryProcessingSagaStep;

    @Qualifier("inventoryCheckingSagaStep")
    private final SagaStep<InventoryRequest> inventoryCheckingSagaStep;

    public InventoryRequestMessageListenerAdapter(SagaStep<InventoryRequest> inventoryProcessingSagaStep, SagaStep<InventoryRequest> inventoryCheckingSagaStep) {
        this.inventoryProcessingSagaStep = inventoryProcessingSagaStep;
        this.inventoryCheckingSagaStep = inventoryCheckingSagaStep;
    }


    @Override
    public void checking(InventoryRequest inventoryRequest) {
        switch (inventoryRequest.orderInventoryStatus()) {
            case CHECKING -> inventoryCheckingSagaStep.process(inventoryRequest);
            case CHECKING_ROLLBACK -> inventoryCheckingSagaStep.rollback(inventoryRequest);
        }
    }


    @Override
    public void processing(InventoryRequest inventoryRequest) {
        switch (inventoryRequest.orderInventoryStatus()) {
            case UPDATING -> inventoryProcessingSagaStep.process(inventoryRequest);
            case UPDATING_ROLLBACK -> inventoryProcessingSagaStep.rollback(inventoryRequest);
        }
    }
}
