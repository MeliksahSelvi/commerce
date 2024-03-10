package com.commerce.inventory.service.adapters.outbox.jpa.exception;

/**
 * @Author mselvi
 * @Created 07.03.2024
 */

public class OrderOutboxNotFoundException extends RuntimeException{

    public OrderOutboxNotFoundException(String message) {
        super(message);
    }
}
