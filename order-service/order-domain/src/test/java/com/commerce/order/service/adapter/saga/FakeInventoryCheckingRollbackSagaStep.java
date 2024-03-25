package com.commerce.order.service.adapter.saga;

import com.commerce.order.service.adapter.helper.FakeInventoryCheckingRollbackHelper;
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
