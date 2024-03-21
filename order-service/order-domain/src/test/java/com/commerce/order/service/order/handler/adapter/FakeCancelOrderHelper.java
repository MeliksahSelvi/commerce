package com.commerce.order.service.order.handler.adapter;

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