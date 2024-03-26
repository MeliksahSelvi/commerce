package com.commerce.inventory.service.adapter;

import com.commerce.inventory.service.saga.InventoryCheckingSagaStep;

/**
 * @Author mselvi
 * @Created 26.03.2024
 */

public class FakeInventoryCheckingSagaStep extends InventoryCheckingSagaStep {
    public FakeInventoryCheckingSagaStep() {
        super(new FakeInventoryCheckingHelper());
    }
}
