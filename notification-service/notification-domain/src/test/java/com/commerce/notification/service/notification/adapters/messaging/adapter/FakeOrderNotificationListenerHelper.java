package com.commerce.notification.service.notification.adapters.messaging.adapter;

import com.commerce.notification.service.customer.adapters.messaging.adapter.FakeCustomerDataAdapter;
import com.commerce.notification.service.notification.adapters.messaging.helper.OrderNotificationListenerHelper;

/**
 * @Author mselvi
 * @Created 21.03.2024
 */

public class FakeOrderNotificationListenerHelper extends OrderNotificationListenerHelper {

    public FakeOrderNotificationListenerHelper() {
        super(new FakeOrderNotificationDataAdapter(), new FakeCustomerDataAdapter(), new FakeMailAdapter());
    }
}
