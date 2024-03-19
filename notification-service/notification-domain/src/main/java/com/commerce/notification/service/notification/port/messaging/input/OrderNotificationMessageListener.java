package com.commerce.notification.service.notification.port.messaging.input;

import com.commerce.notification.service.notification.usecase.OrderNotificationMessage;

/**
 * @Author mselvi
 * @Created 08.03.2024
 */

public interface OrderNotificationMessageListener {

    void processMessage(OrderNotificationMessage orderNotificationMessage);
}
