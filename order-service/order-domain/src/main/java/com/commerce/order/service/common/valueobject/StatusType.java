package com.commerce.order.service.common.valueobject;

/**
 * @Author mselvi
 * @Created 01.03.2024
 */

public enum StatusType {
    ACTIVE(1), PASSIVE(0);

    private final Integer type;

    StatusType(Integer type) {
        this.type = type;
    }

    public Integer getType() {
        return type;
    }

}
