package com.commerce.payment.service.common.exception;

/**
 * @Author mselvi
 * @Created 24.04.2024
 */

public class CustomerNotFoundException extends RuntimeException{

    public CustomerNotFoundException(String message) {
        super(message);
    }
}
