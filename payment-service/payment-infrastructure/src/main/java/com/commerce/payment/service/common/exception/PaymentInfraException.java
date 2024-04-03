package com.commerce.payment.service.common.exception;

/**
 * @Author mselvi
 * @Created 01.04.2024
 */

public class PaymentInfraException extends RuntimeException{

    public PaymentInfraException(String message) {
        super(message);
    }

    public PaymentInfraException(String message, Throwable cause) {
        super(message, cause);
    }
}
