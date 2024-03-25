package com.commerce.order.service.order.adapters.messaging.adapter;

import com.commerce.order.service.saga.InventoryUpdatingRollbackSagaStep;

/**
 * @Author mselvi
 * @Created 25.03.2024
 */

public class FakeInventoryUpdatingRollbackSagaStep extends InventoryUpdatingRollbackSagaStep {
    public FakeInventoryUpdatingRollbackSagaStep() {
        super(new FakeInventoryUpdatingRollbackHelper());
    }
}
