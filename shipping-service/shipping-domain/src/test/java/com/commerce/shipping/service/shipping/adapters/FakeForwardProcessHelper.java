package com.commerce.shipping.service.shipping.adapters;

import com.commerce.shipping.service.shipping.handler.helper.ForwardProcessHelper;

/**
 * @Author mselvi
 * @Created 21.04.2024
 */

public class FakeForwardProcessHelper extends ForwardProcessHelper {
    public FakeForwardProcessHelper() {
        super(new FakeOrderNotificationMessagePublisherAdapter(), new FakeForwardShippingDataAdapter());
    }
}
