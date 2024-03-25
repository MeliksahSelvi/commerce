package com.commerce.notification.service.notification.adapters.messaging;

import com.commerce.notification.service.common.DomainComponent;
import com.commerce.notification.service.notification.adapters.messaging.helper.OrderNotificationListenerHelper;
import com.commerce.notification.service.notification.port.messaging.input.OrderNotificationMessageListener;
import com.commerce.notification.service.notification.usecase.OrderNotificationMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @Author mselvi
 * @Created 08.03.2024
 */

@DomainComponent
public class OrderNotificationMessageListenerAdapter implements OrderNotificationMessageListener {

    private static final Logger logger= LoggerFactory.getLogger(OrderNotificationMessageListenerAdapter.class);
    private final OrderNotificationListenerHelper orderNotificationListenerHelper;

    public OrderNotificationMessageListenerAdapter(OrderNotificationListenerHelper orderNotificationListenerHelper) {
        this.orderNotificationListenerHelper = orderNotificationListenerHelper;
    }

    @Override
    public void processMessage(OrderNotificationMessage orderNotificationMessage) {
        logger.info("OrderNotificationMessage processing action is started");
        orderNotificationListenerHelper.processMessage(orderNotificationMessage);
    }
}
