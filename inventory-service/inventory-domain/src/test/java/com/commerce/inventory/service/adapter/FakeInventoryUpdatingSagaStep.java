package com.commerce.inventory.service.adapter;

import com.commerce.inventory.service.saga.InventoryUpdatingSagaStep;

/**
 * @Author mselvi
 * @Created 26.03.2024
 */

public class FakeInventoryUpdatingSagaStep extends InventoryUpdatingSagaStep {
    public FakeInventoryUpdatingSagaStep() {
        super(new FakeInventoryUpdatingHelper());
    }
}
