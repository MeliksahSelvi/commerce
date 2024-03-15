package com.commerce.order.service.common.valueobject;

/**
 * @Author mselvi
 * @Created 05.03.2024
 */

public enum PaymentStatus {
    COMPLETED("COMPLETED"), CANCELLED("CANCELLED"), FAILED("FAILED");

    private String status;

    PaymentStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return status;
    }
}
