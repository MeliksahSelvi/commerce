package com.commerce.payment.service.common.outbox;

/**
 * @Author mselvi
 * @Created 07.03.2024
 */

public enum OutboxStatus {
    STARTED("STARTED"),COMPLETED("COMPLETED"),FAILED("FAILED");

    private String status;

    OutboxStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return status;
    }
}
