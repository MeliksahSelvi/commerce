package com.commerce.order.service.adapter.saga;

import com.commerce.order.service.adapter.helper.FakeInventoryCheckingHelper;
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
