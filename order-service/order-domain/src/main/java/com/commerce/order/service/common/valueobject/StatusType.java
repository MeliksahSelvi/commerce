package com.commerce.order.service.common.valueobject;

/**
 * @Author mselvi
 * @Created 01.03.2024
 */

public enum StatusType {
    ACTIVE("Active"), PASSIVE("Passive");

    private final String type;

    StatusType(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }

}
