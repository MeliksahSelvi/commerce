package com.commerce.inventory.service.common.exception;

/**
 * @Author mselvi
 * @Created 07.03.2024
 */

public class InventoryDomainException extends RuntimeException{

    public InventoryDomainException(String message) {
        super(message);
    }

    public InventoryDomainException(String message, Throwable cause) {
        super(message, cause);
    }
}
