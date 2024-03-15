package com.commerce.notification.service.common.valueobject;

/**
 * @Author mselvi
 * @Created 08.03.2024
 */

public enum NotificationType {
    APPROVING("APPROVING"),
    SHIPPING("SHIPPING"),
    DELIVERING("DELIVERING"),
    CANCELLING("CANCELLING");

    private String type;

    NotificationType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return type;
    }
}
