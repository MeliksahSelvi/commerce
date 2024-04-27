package com.commerce.order.service.adapter.helper;

import com.commerce.order.service.adapter.*;
import com.commerce.order.service.adapter.order.FakeOrderDataAdapter;
import com.commerce.order.service.adapter.outbox.FakeInventoryOutboxDataAdapter;
import com.commerce.order.service.adapter.outbox.FakePaymentOutboxDataAdapter;
import com.commerce.order.service.saga.helper.InventoryUpdatingHelper;

/**
 * @Author mselvi
 * @Created 25.03.2024
 */

public class FakeInventoryUpdatingHelper extends InventoryUpdatingHelper {
    public FakeInventoryUpdatingHelper() {
        super(new FakeOrderNotificationMessagePublisherAdapter(), new FakeOrderQueryMessagePublisher(), new FakeInventoryOutboxDataAdapter(),
                new FakePaymentOutboxDataAdapter(), new FakeOrderDataAdapter(), new FakeSagaHelper(), new FakeJsonAdapter());
    }
}
