package com.commerce.order.service.common.exception;

/**
 * @Author mselvi
 * @Created 06.03.2024
 */

public class OrderNotFoundException extends RuntimeException{

    public OrderNotFoundException(String message) {
        super(message);
    }
}
