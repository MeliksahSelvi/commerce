package com.commerce.order.service.adapter.helper;

import com.commerce.order.service.adapter.order.FakeOrderDataAdapter;
import com.commerce.order.service.adapter.outbox.FakeInventoryOutboxDataAdapter;
import com.commerce.order.service.adapter.FakeOrderNotificationMessagePublisherAdapter;
import com.commerce.order.service.saga.helper.InventoryUpdatingRollbackHelper;

/**
 * @Author mselvi
 * @Created 25.03.2024
 */

public class FakeInventoryUpdatingRollbackHelper extends InventoryUpdatingRollbackHelper {
    public FakeInventoryUpdatingRollbackHelper() {
        super(new FakeOrderNotificationMessagePublisherAdapter(), new FakeInventoryOutboxDataAdapter(), new FakeOrderDataAdapter(), new FakeSagaHelper());
    }
}
