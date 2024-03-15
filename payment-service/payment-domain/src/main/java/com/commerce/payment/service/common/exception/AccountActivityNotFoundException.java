package com.commerce.payment.service.common.exception;

/**
 * @Author mselvi
 * @Created 08.03.2024
 */

public class AccountActivityNotFoundException extends RuntimeException{

    public AccountActivityNotFoundException(String message) {
        super(message);
    }
}
