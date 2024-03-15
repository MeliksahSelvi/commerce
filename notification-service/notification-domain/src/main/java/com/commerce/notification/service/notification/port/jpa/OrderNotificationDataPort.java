package com.commerce.notification.service.notification.port.jpa;

import com.commerce.notification.service.common.valueobject.NotificationStatus;
import com.commerce.notification.service.notification.entity.OrderNotification;

import java.util.Optional;

/**
 * @Author mselvi
 * @Created 08.03.2024
 */

public interface OrderNotificationDataPort {

    Optional<OrderNotification> findByOrderIdAndNotificationStatus(Long orderId, NotificationStatus notificationStatus);

    OrderNotification save(OrderNotification orderNotification);
}
