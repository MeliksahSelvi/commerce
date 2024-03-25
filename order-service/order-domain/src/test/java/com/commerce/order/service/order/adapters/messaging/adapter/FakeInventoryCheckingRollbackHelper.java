package com.commerce.order.service.order.adapters.messaging.adapter;

import com.commerce.order.service.order.handler.adapter.FakeInventoryOutboxDataAdapter;
import com.commerce.order.service.order.handler.adapter.FakeOrderDataAdapter;
import com.commerce.order.service.order.handler.adapter.FakeSagaHelper;
import com.commerce.order.service.saga.helper.InventoryCheckingRollbackHelper;

/**
 * @Author mselvi
 * @Created 25.03.2024
 */

public class FakeInventoryCheckingRollbackHelper extends InventoryCheckingRollbackHelper {
    public FakeInventoryCheckingRollbackHelper() {
        super(new FakeInventoryOutboxDataAdapter(), new FakeOrderDataAdapter(), new FakeSagaHelper());
    }
}
