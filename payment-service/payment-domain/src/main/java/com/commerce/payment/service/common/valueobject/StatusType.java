package com.commerce.payment.service.common.valueobject;

/**
 * @Author mselvi
 * @Created 07.03.2024
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
