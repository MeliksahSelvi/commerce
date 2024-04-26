package com.commerce.notification.service.customer.adapters.messaging.adapter;

import com.commerce.notification.service.customer.adapters.messaging.helper.CustomerCommandListenerHelper;

/**
 * @Author mselvi
 * @Created 26.04.2024
 */

public class FakeCustomerCommandListenerHelper extends CustomerCommandListenerHelper {
    public FakeCustomerCommandListenerHelper() {
        super(new FakeCustomerDataAdapter());
    }
}
