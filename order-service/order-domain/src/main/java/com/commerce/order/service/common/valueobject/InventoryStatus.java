package com.commerce.order.service.common.valueobject;

/**
 * @Author mselvi
 * @Created 04.03.2024
 */

public enum InventoryStatus {
    AVAILABLE("AVAILABLE"),
    NON_AVAILABLE("NON_AVAILABLE");

    private String status;

    InventoryStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return status;
    }
}
