package com.commerce.shipping.service.shipping.adapters.messaging;

import com.commerce.shipping.service.common.DomainComponent;
import com.commerce.shipping.service.shipping.port.messaging.input.OrderNotificationMessageListener;
import com.commerce.shipping.service.shipping.usecase.OrderNotificationMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @Author mselvi
 * @Created 09.03.2024
 */

@DomainComponent
public class OrderNotificationMessageListenerAdapter implements OrderNotificationMessageListener {

    private static final Logger logger = LoggerFactory.getLogger(OrderNotificationMessageListenerAdapter.class);
    private final OrderNotificationListenerHelper orderNotificationListenerHelper;

    public OrderNotificationMessageListenerAdapter(OrderNotificationListenerHelper orderNotificationListenerHelper) {
        this.orderNotificationListenerHelper = orderNotificationListenerHelper;
    }

    @Override
    public void approving(OrderNotificationMessage message) {
        logger.info("Order approving action started by orderId: {}", message.orderId());
        orderNotificationListenerHelper.approving(message);
    }

    @Override
    public void cancelling(OrderNotificationMessage message) {
        logger.info("Order cancelling action started by orderId: {}", message.orderId());
        orderNotificationListenerHelper.cancelling(message);
    }
}
