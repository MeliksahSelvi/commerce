package com.commerce.user.service.common.valueobject;

/**
 * @Author mselvi
 * @Created 31.03.2024
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
