package com.commerce.order.service.adapter.saga;

import com.commerce.order.service.adapter.helper.FakeInventoryUpdatingHelper;
import com.commerce.order.service.saga.InventoryUpdatingSagaStep;

/**
 * @Author mselvi
 * @Created 25.03.2024
 */

public class FakeInventoryUpdatingSagaStep extends InventoryUpdatingSagaStep {
    public FakeInventoryUpdatingSagaStep() {
        super(new FakeInventoryUpdatingHelper());
    }
}
