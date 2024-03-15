package com.commerce.order.service.order.port.messaging.output;

import com.commerce.order.service.order.usecase.OrderNotificationMessage;

/**
 * @Author mselvi
 * @Created 08.03.2024
 */

public interface OrderNotificationMessagePublisher {

    void publish(OrderNotificationMessage orderNotificationMessage);
}
