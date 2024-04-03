package com.commerce.user.service.common.exception;

/**
 * @Author mselvi
 * @Created 01.04.2024
 */

public class RoleNotFoundException extends RuntimeException{

    public RoleNotFoundException(String message) {
        super(message);
    }
}
