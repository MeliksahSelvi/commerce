package com.commerce.order.service.adapters.outbox.jpa.exception;

/**
 * @Author mselvi
 * @Created 06.03.2024
 */

public class PaymentOutboxNotFoundException extends RuntimeException{

    public PaymentOutboxNotFoundException(String message) {
        super(message);
    }
}
