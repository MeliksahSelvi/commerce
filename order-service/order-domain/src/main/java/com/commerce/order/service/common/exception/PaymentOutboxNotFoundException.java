package com.commerce.order.service.common.exception;

/**
 * @Author mselvi
 * @Created 19.03.2024
 */

public class PaymentOutboxNotFoundException extends RuntimeException{

    public PaymentOutboxNotFoundException(String message) {
        super(message);
    }
}
