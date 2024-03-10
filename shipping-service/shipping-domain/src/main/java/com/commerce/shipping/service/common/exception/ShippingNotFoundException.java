package com.commerce.shipping.service.common.exception;

/**
 * @Author mselvi
 * @Created 09.03.2024
 */

public class ShippingNotFoundException extends RuntimeException{

    public ShippingNotFoundException(String message) {
        super(message);
    }
}
