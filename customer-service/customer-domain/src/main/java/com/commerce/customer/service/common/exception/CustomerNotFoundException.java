package com.commerce.customer.service.common.exception;

/**
 * @Author mselvi
 * @Created 10.03.2024
 */

public class CustomerNotFoundException extends RuntimeException{

    public CustomerNotFoundException(String message) {
        super(message);
    }
}
