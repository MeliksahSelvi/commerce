package com.commerce.notification.service.common.valueobject;

/**
 * @Author mselvi
 * @Created 08.03.2024
 */

public enum StatusType {
    ACTIVE("ACTIVE"), PASSIVE("PASSIVE");

    private final String type;

    StatusType(String type) {
        this.type = type;
    }

    public String toString() {
        return type;
    }
}
