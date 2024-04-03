package com.commerce.inventory.service.common.exception;

/**
 * @Author mselvi
 * @Created 01.04.2024
 */

public class InventoryInfraException extends RuntimeException{

    public InventoryInfraException(String message) {
        super(message);
    }

    public InventoryInfraException(String message, Throwable cause) {
        super(message, cause);
    }
}
