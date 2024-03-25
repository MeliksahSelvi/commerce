package com.commerce.order.service.order.adapters.messaging.adapter;

import com.commerce.order.service.order.handler.adapter.FakeInventoryOutboxDataAdapter;
import com.commerce.order.service.order.handler.adapter.FakeSagaHelper;
import com.commerce.order.service.saga.helper.InventoryUpdatingRollbackHelper;

/**
 * @Author mselvi
 * @Created 25.03.2024
 */

public class FakeInventoryUpdatingRollbackHelper extends InventoryUpdatingRollbackHelper {
    public FakeInventoryUpdatingRollbackHelper() {
        super(new FakeOrderNotificationMessagePublisherAdapter(), new FakeInventoryOutboxDataAdapter(), new FakeCheckingOrderDataAdapter(), new FakeSagaHelper());
    }
}
