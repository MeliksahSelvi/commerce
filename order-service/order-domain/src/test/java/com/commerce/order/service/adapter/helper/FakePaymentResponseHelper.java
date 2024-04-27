package com.commerce.order.service.adapter.helper;

import com.commerce.order.service.adapter.FakeJsonAdapter;
import com.commerce.order.service.adapter.FakeOrderQueryMessagePublisher;
import com.commerce.order.service.adapter.order.FakeOrderDataAdapter;
import com.commerce.order.service.adapter.outbox.FakeInventoryOutboxDataAdapter;
import com.commerce.order.service.adapter.outbox.FakePaymentOutboxDataAdapter;
import com.commerce.order.service.saga.helper.PaymentResponseHelper;

/**
 * @Author mselvi
 * @Created 25.03.2024
 */

public class FakePaymentResponseHelper extends PaymentResponseHelper {
    public FakePaymentResponseHelper() {
        super(new FakeOrderQueryMessagePublisher(), new FakeInventoryOutboxDataAdapter(), new FakePaymentOutboxDataAdapter(),
                new FakeOrderDataAdapter(), new FakeSagaHelper(), new FakeJsonAdapter());
    }
}
