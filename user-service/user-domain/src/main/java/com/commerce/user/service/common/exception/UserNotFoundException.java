package com.commerce.user.service.common.exception;

/**
 * @Author mselvi
 * @Created 31.03.2024
 */

public class UserNotFoundException extends RuntimeException{

    public UserNotFoundException(String message) {
        super(message);
    }
}
