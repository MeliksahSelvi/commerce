package com.commerce.order.service.order.adapters.messaging.adapter;

import com.commerce.order.service.order.handler.adapter.*;
import com.commerce.order.service.saga.helper.InventoryUpdatingHelper;

/**
 * @Author mselvi
 * @Created 25.03.2024
 */

public class FakeInventoryUpdatingHelper extends InventoryUpdatingHelper {
    public FakeInventoryUpdatingHelper() {
        super(new FakeOrderNotificationMessagePublisherAdapter(), new FakeInventoryOutboxDataAdapter(), new FakePaymentOutboxDataAdapter(),
                new FakeOrderDataAdapter(), new FakeSagaHelper(), new FakeJsonAdapter());
    }
}
