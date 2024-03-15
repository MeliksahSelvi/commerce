package com.commerce.payment.service.common.valueobject;

/**
 * @Author mselvi
 * @Created 07.03.2024
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
