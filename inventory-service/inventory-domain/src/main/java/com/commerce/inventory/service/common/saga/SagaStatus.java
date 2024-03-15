package com.commerce.inventory.service.common.saga;

/**
 * @Author mselvi
 * @Created 10.03.2024
 */

public enum SagaStatus {
    CHECKING("CHECKING"),
    PAYING("PAYING"),
    PROCESSING("PROCESSING"),
    SUCCEEDED("SUCCEEDED"),
    CANCELLING("CANCELLING"),
    CANCELLED("CANCELLED");

    private String status;

    SagaStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return status;
    }
}
