package com.commerce.payment.service.common.exception;

/**
 * @Author mselvi
 * @Created 07.03.2024
 */

public class PaymentDomainException extends RuntimeException {
    public PaymentDomainException(String message) {
        super(message);
    }

    public PaymentDomainException(String message, Throwable cause) {
        super(message, cause);
    }
}
