package com.commerce.shipping.service.shipping.port.messaging.input;

import com.commerce.shipping.service.shipping.usecase.OrderNotificationMessage;

/**
 * @Author mselvi
 * @Created 09.03.2024
 */

public interface OrderNotificationMessageListener {

    void approving(OrderNotificationMessage orderNotificationMessage);

    void cancelling(OrderNotificationMessage orderNotificationMessage);
}
