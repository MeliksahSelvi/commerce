package com.commerce.payment.service.adapter;

import com.commerce.payment.service.payment.adapters.messaging.helper.PaymentRequestListenerHelper;

/**
 * @Author mselvi
 * @Created 21.03.2024
 */

public class FakePaymentRequestListenerHelper extends PaymentRequestListenerHelper {
    public FakePaymentRequestListenerHelper() {
        super(new FakeAccountActivityDataAdapter(), new FakeOrderOutboxDataAdapter(),
                new FakeAccountDataAdapter(), new FakePaymentDataAdapter(), new FakeJsonAdapter());
    }
}
