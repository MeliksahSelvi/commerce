package com.commerce.inventory.service.common.valueobject;

/**
 * @Author mselvi
 * @Created 07.03.2024
 */

public record Availability(boolean value) {

    public boolean isAvailable() {
        return value;
    }

}
