package com.commerce.customer.service.common.exception;

/**
 * @Author mselvi
 * @Created 01.04.2024
 */

public class CustomerInfraException extends RuntimeException{

    public CustomerInfraException(String message) {
        super(message);
    }

    public CustomerInfraException(String message, Throwable cause) {
        super(message, cause);
    }
}
