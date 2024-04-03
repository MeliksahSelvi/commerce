package com.commerce.order.service.common.exception;

/**
 * @Author mselvi
 * @Created 01.04.2024
 */

public class OrderInfraException extends RuntimeException{

    public OrderInfraException(String message) {
        super(message);
    }

    public OrderInfraException(String message, Throwable cause) {
        super(message, cause);
    }
}
