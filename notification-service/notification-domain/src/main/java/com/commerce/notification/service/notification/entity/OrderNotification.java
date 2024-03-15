package com.commerce.notification.service.notification.entity;

import com.commerce.notification.service.common.model.BaseEntity;
import com.commerce.notification.service.common.valueobject.NotificationStatus;

/**
 * @Author mselvi
 * @Created 08.03.2024
 */

public class OrderNotification extends BaseEntity {

    private final Long orderId;
    private final Long customerId;
    private NotificationStatus notificationStatus;
    private String message;

    private OrderNotification(Builder builder) {
        setId(builder.id);
        this.orderId = builder.orderId;
        this.customerId = builder.customerId;
        this.notificationStatus = builder.notificationStatus;
        this.message = builder.message;
    }

    public static Builder builder() {
        return new Builder();
    }

    public Long getOrderId() {
        return orderId;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public NotificationStatus getNotificationStatus() {
        return notificationStatus;
    }

    public String getMessage() {
        return message;
    }

    public static final class Builder {
        private Long id;
        private Long orderId;
        private Long customerId;
        private NotificationStatus notificationStatus;
        private String message;

        private Builder() {
        }

        public Builder id(Long val) {
            this.id = val;
            return this;
        }

        public Builder orderId(Long val) {
            this.orderId = val;
            return this;
        }

        public Builder customerId(Long val) {
            this.customerId = val;
            return this;
        }

        public Builder message(String val) {
            this.message = val;
            return this;
        }

        public Builder notificationStatus(NotificationStatus val) {
            this.notificationStatus = val;
            return this;
        }

        public OrderNotification build() {
            return new OrderNotification(this);
        }
    }
}
