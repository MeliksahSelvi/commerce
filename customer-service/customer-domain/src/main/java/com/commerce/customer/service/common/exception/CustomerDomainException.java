package com.commerce.customer.service.common.exception;

/**
 * @Author mselvi
 * @Created 10.03.2024
 */

public class CustomerDomainException extends RuntimeException{
    public CustomerDomainException(String message) {
        super(message);
    }
}
