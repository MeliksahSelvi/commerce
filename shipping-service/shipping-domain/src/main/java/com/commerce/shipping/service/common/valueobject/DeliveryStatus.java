package com.commerce.shipping.service.common.valueobject;

/**
 * @Author mselvi
 * @Created 09.03.2024
 */

public enum DeliveryStatus {
    APPROVED("APPROVED"), SHIPPED("SHIPPED"), DELIVERED("DELIVERED");

    private String status;

    DeliveryStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return status;
    }
}
