package com.commerce.shipping.service.shipping.port.messaging.output;

import com.commerce.shipping.service.shipping.usecase.OrderNotificationMessage;

/**
 * @Author mselvi
 * @Created 09.03.2024
 */

public interface OrderNotificationMessagePublisher {

    void publish(OrderNotificationMessage orderNotificationMessage);
}
