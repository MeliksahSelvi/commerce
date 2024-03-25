package com.commerce.order.service.order.adapters.messaging.adapter;

import com.commerce.order.service.saga.InventoryCheckingRollbackSagaStep;

/**
 * @Author mselvi
 * @Created 25.03.2024
 */

public class FakeInventoryCheckingRollbackSagaStep extends InventoryCheckingRollbackSagaStep {

    public FakeInventoryCheckingRollbackSagaStep() {
        super(new FakeInventoryCheckingRollbackHelper());
    }
}
