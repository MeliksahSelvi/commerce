package com.commerce.order.service.common.valueobject;

/**
 * @Author mselvi
 * @Created 01.03.2024
 */

public enum OrderStatus {
    CHECKING("CHECKING"),
    PENDING("PENDING"),
    PAID("PAID"),
    APPROVED("APPROVED"),
    CANCELLING("CANCELLING"),
    CANCELLED("CANCELLED");

    private String status;

    OrderStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return status;
    }
}
