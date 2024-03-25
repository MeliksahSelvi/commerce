package com.commerce.order.service.adapter.helper;

import com.commerce.order.service.adapter.FakeJsonAdapter;
import com.commerce.order.service.adapter.order.FakeOrderDataAdapter;
import com.commerce.order.service.adapter.outbox.FakeInventoryOutboxDataAdapter;
import com.commerce.order.service.adapter.outbox.FakePaymentOutboxDataAdapter;
import com.commerce.order.service.order.handler.helper.CancelOrderHelper;

/**
 * @Author mselvi
 * @Created 21.03.2024
 */

public class FakeCancelOrderHelper extends CancelOrderHelper {
    public FakeCancelOrderHelper() {
        super(new FakeInventoryOutboxDataAdapter(), new FakePaymentOutboxDataAdapter(), new FakeOrderDataAdapter(), new FakeSagaHelper(), new FakeJsonAdapter());
    }
}
