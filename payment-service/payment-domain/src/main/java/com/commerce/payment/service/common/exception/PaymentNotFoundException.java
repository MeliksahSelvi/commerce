package com.commerce.payment.service.common.exception;

/**
 * @Author mselvi
 * @Created 08.03.2024
 */

public class PaymentNotFoundException extends RuntimeException{

    public PaymentNotFoundException(String message) {
        super(message);
    }
}
