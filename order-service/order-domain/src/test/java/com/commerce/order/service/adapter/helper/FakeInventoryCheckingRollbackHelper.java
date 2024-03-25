package com.commerce.order.service.adapter.helper;

import com.commerce.order.service.adapter.order.FakeOrderDataAdapter;
import com.commerce.order.service.adapter.outbox.FakeInventoryOutboxDataAdapter;
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
