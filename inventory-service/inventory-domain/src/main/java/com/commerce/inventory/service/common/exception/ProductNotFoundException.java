package com.commerce.inventory.service.common.exception;

/**
 * @Author mselvi
 * @Created 07.03.2024
 */

public class ProductNotFoundException extends RuntimeException{

    public ProductNotFoundException(String message) {
        super(message);
    }
}
