package com.commerce.payment.service.payment.adapter;

import com.commerce.payment.service.customer.adapter.FakeCustomerDataAdapter;
import com.commerce.payment.service.payment.adapters.messaging.helper.PaymentRequestListenerHelper;

/**
 * @Author mselvi
 * @Created 21.03.2024
 */

public class FakePaymentRequestListenerHelper extends PaymentRequestListenerHelper {
    public FakePaymentRequestListenerHelper() {
        super(new FakeAccountActivityDataAdapter(), new FakeOrderOutboxDataAdapter(),
                new FakeCustomerDataAdapter(), new FakeAccountDataAdapter(), new FakePaymentDataAdapter(), new FakeJsonAdapter());
    }
}
