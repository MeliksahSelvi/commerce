package com.commerce.order.query.service.common.exception;

/**
 * @Author mselvi
 * @Created 27.04.2024
 */

public class OrderQueryInfraException extends RuntimeException {

    public OrderQueryInfraException(String message) {
        super(message);
    }

    public OrderQueryInfraException(String message, Throwable cause) {
        super(message, cause);
    }
}
