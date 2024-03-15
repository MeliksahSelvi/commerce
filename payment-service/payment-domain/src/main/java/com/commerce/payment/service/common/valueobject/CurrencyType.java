package com.commerce.payment.service.common.valueobject;

/**
 * @Author mselvi
 * @Created 07.03.2024
 */

public enum CurrencyType {
    TL("TL"),
    EURO("EURO"),
    DOLLAR("DOLLAR");

    private final String type;

    CurrencyType(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }

    @Override
    public String toString() {
        return type;
    }
}
