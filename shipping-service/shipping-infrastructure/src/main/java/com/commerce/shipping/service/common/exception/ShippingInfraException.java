package com.commerce.shipping.service.common.exception;

/**
 * @Author mselvi
 * @Created 01.04.2024
 */

public class ShippingInfraException extends RuntimeException{

    public ShippingInfraException(String message) {
        super(message);
    }

    public ShippingInfraException(String message, Throwable cause) {
        super(message, cause);
    }
}
