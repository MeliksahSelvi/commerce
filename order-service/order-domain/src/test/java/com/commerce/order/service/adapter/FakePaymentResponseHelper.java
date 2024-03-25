package com.commerce.order.service.adapter;

import com.commerce.order.service.saga.helper.PaymentResponseHelper;

/**
 * @Author mselvi
 * @Created 25.03.2024
 */

public class FakePaymentResponseHelper extends PaymentResponseHelper {
    public FakePaymentResponseHelper() {
        super(new FakeInventoryOutboxDataAdapter(), new FakePaymentOutboxDataAdapter(), new FakePendingOrderDataAdapter(), new FakeSagaHelper(), new FakeJsonAdapter());
    }
}
