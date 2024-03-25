package com.commerce.order.service.adapter.helper;

import com.commerce.order.service.adapter.FakeJsonAdapter;
import com.commerce.order.service.adapter.order.FakeOrderDataAdapter;
import com.commerce.order.service.adapter.outbox.FakeInventoryOutboxDataAdapter;
import com.commerce.order.service.adapter.outbox.FakePaymentOutboxDataAdapter;
import com.commerce.order.service.saga.helper.InventoryCheckingHelper;

/**
 * @Author mselvi
 * @Created 25.03.2024
 */

public class FakeInventoryCheckingHelper extends InventoryCheckingHelper {

    public FakeInventoryCheckingHelper() {
        super(new FakeInventoryOutboxDataAdapter(), new FakePaymentOutboxDataAdapter(), new FakeOrderDataAdapter(), new FakeSagaHelper(), new FakeJsonAdapter());
    }
}
