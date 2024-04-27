package com.commerce.order.query.service.common.exception;

/**
 * @Author mselvi
 * @Created 27.04.2024
 */

public class OrderNotFoundException extends RuntimeException{

    public OrderNotFoundException(String message) {
        super(message);
    }
}
