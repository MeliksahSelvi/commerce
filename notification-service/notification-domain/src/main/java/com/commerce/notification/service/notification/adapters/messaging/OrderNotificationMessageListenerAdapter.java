package com.commerce.notification.service.notification.adapters.messaging;

import com.commerce.notification.service.common.DomainComponent;
import com.commerce.notification.service.notification.adapters.messaging.helper.OrderNotificationListenerHelper;
import com.commerce.notification.service.notification.port.messaging.input.OrderNotificationMessageListener;
import com.commerce.notification.service.notification.usecase.OrderNotificationMessage;

/**
 * @Author mselvi
 * @Created 08.03.2024
 */

@DomainComponent
public class OrderNotificationMessageListenerAdapter implements OrderNotificationMessageListener {

    private final OrderNotificationListenerHelper orderNotificationListenerHelper;

    public OrderNotificationMessageListenerAdapter(OrderNotificationListenerHelper orderNotificationListenerHelper) {
        this.orderNotificationListenerHelper = orderNotificationListenerHelper;
    }

    @Override
    public void processMessage(OrderNotificationMessage orderNotificationMessage) {
        orderNotificationListenerHelper.processMessage(orderNotificationMessage);
    }
}
