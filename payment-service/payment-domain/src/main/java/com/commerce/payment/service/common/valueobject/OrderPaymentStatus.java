package com.commerce.payment.service.common.valueobject;

/**
 * @Author mselvi
 * @Created 07.03.2024
 */

public enum OrderPaymentStatus {
    PENDING("PENDING"),CANCELLED("CANCELLED");

    private String status;

    OrderPaymentStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return status;
    }
}
