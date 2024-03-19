package com.commerce.order.service.common.exception;

/**
 * @Author mselvi
 * @Created 19.03.2024
 */

public class InventoryOutboxNotFoundException extends RuntimeException{

    public InventoryOutboxNotFoundException(String message) {
        super(message);
    }
}
