package com.commerce.notification.service.adapters.notification.jpa;

import com.commerce.notification.service.adapters.notification.jpa.entity.OrderNotificationEntity;
import com.commerce.notification.service.adapters.notification.jpa.repository.OrderNotificationEntityRepository;
import com.commerce.notification.service.common.valueobject.NotificationStatus;
import com.commerce.notification.service.notification.model.OrderNotification;
import com.commerce.notification.service.notification.port.jpa.OrderNotificationDataPort;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * @Author mselvi
 * @Created 08.03.2024
 */

@Service
public class OrderNotificationDataAdapter implements OrderNotificationDataPort {

    private final OrderNotificationEntityRepository repository;

    public OrderNotificationDataAdapter(OrderNotificationEntityRepository repository) {
        this.repository = repository;
    }

    @Override
    public Optional<OrderNotification> findByOrderIdAndNotificationStatus(Long orderId, NotificationStatus notificationStatus) {
        Optional<OrderNotificationEntity> orderNotificationOptional = repository.findByOrderIdAndNotificationStatus(orderId, notificationStatus);
        return orderNotificationOptional.map(OrderNotificationEntity::toModel);
    }

    @Override
    public OrderNotification save(OrderNotification orderNotification) {
        var orderNotificationEntity = new OrderNotificationEntity();
        orderNotificationEntity.setId(orderNotification.getId());
        orderNotificationEntity.setOrderId(orderNotification.getOrderId());
        orderNotificationEntity.setCustomerId(orderNotification.getCustomerId());
        orderNotificationEntity.setNotificationStatus(orderNotification.getNotificationStatus());
        orderNotificationEntity.setMessage(orderNotification.getMessage());
        return repository.save(orderNotificationEntity).toModel();
    }
}
