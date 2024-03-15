package com.commerce.order.service.common.valueobject;

/**
 * @Author mselvi
 * @Created 02.03.2024
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
