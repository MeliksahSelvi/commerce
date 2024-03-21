package com.commerce.shipping.service.shipping.adapters;

import com.commerce.shipping.service.shipping.port.messaging.output.OrderNotificationMessagePublisher;
import com.commerce.shipping.service.shipping.usecase.OrderNotificationMessage;

/**
 * @Author mselvi
 * @Created 21.03.2024
 */

public class FakeOrderNotificationMessagePublisherAdapter implements OrderNotificationMessagePublisher {

    @Override
    public void publish(OrderNotificationMessage orderNotificationMessage) {

    }
}
