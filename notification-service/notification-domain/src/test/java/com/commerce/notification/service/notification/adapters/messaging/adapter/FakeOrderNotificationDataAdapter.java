package com.commerce.notification.service.notification.adapters.messaging.adapter;

import com.commerce.notification.service.common.valueobject.NotificationStatus;
import com.commerce.notification.service.notification.entity.OrderNotification;
import com.commerce.notification.service.notification.port.jpa.OrderNotificationDataPort;

import java.util.Optional;

/**
 * @Author mselvi
 * @Created 21.03.2024
 */

public class FakeOrderNotificationDataAdapter implements OrderNotificationDataPort {

    private static final Long ALREADY_SEND_ORDER_ID = 1L;

    @Override
    public Optional<OrderNotification> findByOrderIdAndNotificationStatus(Long orderId, NotificationStatus notificationStatus) {
        if (ALREADY_SEND_ORDER_ID != orderId) {
            return Optional.empty();
        }
        return Optional.of(
                OrderNotification.builder()
                        .id(1L)
                        .customerId(1L)
                        .orderId(orderId)
                        .notificationStatus(notificationStatus)
                        .message("message")
                        .build()
        );
    }

    @Override
    public OrderNotification save(OrderNotification orderNotification) {
        return OrderNotification.builder()
                .id(orderNotification.getId())
                .orderId(orderNotification.getOrderId())
                .customerId(orderNotification.getCustomerId())
                .notificationStatus(orderNotification.getNotificationStatus())
                .message(orderNotification.getMessage())
                .build();
    }
}
