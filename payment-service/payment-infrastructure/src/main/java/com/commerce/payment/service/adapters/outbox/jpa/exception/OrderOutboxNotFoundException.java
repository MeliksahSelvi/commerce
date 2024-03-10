package com.commerce.payment.service.adapters.outbox.jpa.exception;

/**
 * @Author mselvi
 * @Created 08.03.2024
 */

public class OrderOutboxNotFoundException extends RuntimeException{

    public OrderOutboxNotFoundException(String message) {
        super(message);
    }
}
