package com.commerce.inventory.service.common.valueobject;

/**
 * @Author mselvi
 * @Created 07.03.2024
 */

public enum OrderInventoryStatus {
    CHECKING("CHECKING"),
    CHECKING_ROLLBACK("CHECKING_ROLLBACK"),
    UPDATING("PROCESSING"),
    UPDATING_ROLLBACK("PROCESSING_ROLLBACK");

    private String status;

    OrderInventoryStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return status;
    }
}
