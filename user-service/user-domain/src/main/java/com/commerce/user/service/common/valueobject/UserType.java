package com.commerce.user.service.common.valueobject;

/**
 * @Author mselvi
 * @Created 31.03.2024
 */

public enum UserType {
    ADMIN("ADMIN"), CUSTOMER("CUSTOMER");

    private final String type;

    UserType(String type) {
        this.type = type;
    }

    public String toString() {
        return type;
    }
}
