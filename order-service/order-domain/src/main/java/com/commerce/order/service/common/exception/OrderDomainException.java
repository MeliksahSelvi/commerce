package com.commerce.order.service.common.exception;

/**
 * @Author mselvi
 * @Created 01.03.2024
 */

public class OrderDomainException extends RuntimeException{

    public OrderDomainException(String message) {
        super(message);
    }

    public OrderDomainException(String message, Throwable cause) {
        super(message, cause);
    }
}
