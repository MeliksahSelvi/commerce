package com.commerce.order.service.adapters.outbox.jpa.exception;

/**
 * @Author mselvi
 * @Created 06.03.2024
 */

public class InventoryOutboxNotFoundException extends RuntimeException{

    public InventoryOutboxNotFoundException(String message) {
        super(message);
    }
}
