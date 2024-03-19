package com.commerce.payment.service.common.valueobject;

/**
 * @Author mselvi
 * @Created 07.03.2024
 */

public enum ActivityType {

    WITHDRAW("WITHDRAW"),//para çekme
    DEPOSIT("DEPOSIT"),//para yatırma
    SEND("SEND"),//para gönderme havale
    GET("GET"),//para alma havale
    SPEND("SPEND"),// para harcama
    PAYBACK("PAYBACK"),
    FAIL("FAIL");//iade

    private final String type;

    ActivityType(String type) {
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
