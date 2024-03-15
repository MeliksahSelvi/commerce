package com.commerce.notification.service.common.valueobject;

/**
 * @Author mselvi
 * @Created 08.03.2024
 */

public enum NotificationStatus {
    APPROVED("APPROVED"),
    SHIPPED("SHIPPED"),
    DELIVERED("DELIVERED"),
    CANCELLED("CANCELLED");

    private String status;

    NotificationStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return status;
    }
}
