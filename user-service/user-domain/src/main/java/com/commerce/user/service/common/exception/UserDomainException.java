package com.commerce.user.service.common.exception;

/**
 * @Author mselvi
 * @Created 31.03.2024
 */

public class UserDomainException extends RuntimeException{

    public UserDomainException(String message) {
        super(message);
    }

    public UserDomainException(String message, Throwable cause) {
        super(message, cause);
    }
}
