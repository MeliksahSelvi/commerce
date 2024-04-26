package com.commerce.notification.service.common.exception;

/**
 * @Author mselvi
 * @Created 26.04.2024
 */

public class CustomerNotFoundException extends RuntimeException{

    public CustomerNotFoundException(String message) {
        super(message);
    }
}
