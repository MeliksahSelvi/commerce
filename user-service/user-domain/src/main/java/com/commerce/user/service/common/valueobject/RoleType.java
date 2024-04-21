package com.commerce.user.service.common.valueobject;

/**
 * @Author mselvi
 * @Created 31.03.2024
 */

public enum RoleType {
    ADMIN("ADMIN"), MANAGER("MANAGER"),CUSTOMER("CUSTOMER");

    private final String type;

    RoleType(String type) {
        this.type = type;
    }

    public String toString() {
        return type;
    }
}
