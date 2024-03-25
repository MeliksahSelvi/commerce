package com.commerce.order.service.adapter;

import com.commerce.order.service.order.port.messaging.output.OrderNotificationMessagePublisher;
import com.commerce.order.service.order.usecase.OrderNotificationMessage;

/**
 * @Author mselvi
 * @Created 25.03.2024
 */

public class FakeOrderNotificationMessagePublisherAdapter implements OrderNotificationMessagePublisher {

    @Override
    public void publish(OrderNotificationMessage orderNotificationMessage) {

    }
}
