package com.commerce.user.service.common.exception;

/**
 * @Author mselvi
 * @Created 01.04.2024
 */

public class UserInfraException extends RuntimeException{

    public UserInfraException(String message) {
        super(message);
    }

    public UserInfraException(String message, Throwable cause) {
        super(message, cause);
    }
}
