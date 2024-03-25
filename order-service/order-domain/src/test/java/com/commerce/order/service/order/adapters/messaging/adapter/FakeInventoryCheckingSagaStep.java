package com.commerce.order.service.order.adapters.messaging.adapter;

import com.commerce.order.service.saga.InventoryCheckingSagaStep;

/**
 * @Author mselvi
 * @Created 25.03.2024
 */

public class FakeInventoryCheckingSagaStep extends InventoryCheckingSagaStep {

    public FakeInventoryCheckingSagaStep() {
        super(new FakeInventoryCheckingHelper());
    }
}
