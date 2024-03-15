package com.commerce.payment.service.common.exception;

/**
 * @Author mselvi
 * @Created 08.03.2024
 */

public class AccountNotFoundException extends RuntimeException{

    public AccountNotFoundException(String message) {
        super(message);
    }
}
