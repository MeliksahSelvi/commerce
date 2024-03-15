package com.commerce.notification.service.adapters.notification.jpa.entity;

import com.commerce.notification.service.common.model.AbstractEntity;
import com.commerce.notification.service.common.valueobject.NotificationStatus;
import com.commerce.notification.service.notification.entity.OrderNotification;
import jakarta.persistence.*;

/**
 * @Author mselvi
 * @Created 08.03.2024
 */

@Entity
@Table(name = "ORDER_NOTIFICATION")
public class OrderNotificationEntity extends AbstractEntity {

    @Column(name = "ORDER_ID")
    private Long orderId;

    @Column(name = "CUSTOMER_ID")
    private Long customerId;

    @Column(name = "NOTIFICATION_TYPE")
    @Enumerated(EnumType.STRING)
    private NotificationStatus notificationStatus;

    @Column(name = "MESSAGE")
    private String message;

    public OrderNotification toModel() {
        return OrderNotification.builder()
                .id(getId())
                .orderId(orderId)
                .customerId(customerId)
                .notificationStatus(notificationStatus)
                .message(message)
                .build();
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public NotificationStatus getNotificationStatus() {
        return notificationStatus;
    }

    public void setNotificationStatus(NotificationStatus notificationStatus) {
        this.notificationStatus = notificationStatus;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
