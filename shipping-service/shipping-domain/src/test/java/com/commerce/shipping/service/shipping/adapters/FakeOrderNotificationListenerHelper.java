package com.commerce.shipping.service.shipping.adapters;

import com.commerce.shipping.service.shipping.adapters.messaging.OrderNotificationListenerHelper;

/**
 * @Author mselvi
 * @Created 21.04.2024
 */

public class FakeOrderNotificationListenerHelper extends OrderNotificationListenerHelper {
    public FakeOrderNotificationListenerHelper() {
        super(new FakeOrderNotificationShippingDataAdapter());
    }
}
