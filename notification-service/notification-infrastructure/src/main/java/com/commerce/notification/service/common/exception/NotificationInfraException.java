package com.commerce.notification.service.common.exception;

/**
 * @Author mselvi
 * @Created 03.04.2024
 */

public class NotificationInfraException extends RuntimeException{

    public NotificationInfraException(String message) {
        super(message);
    }

    public NotificationInfraException(String message, Throwable cause) {
        super(message, cause);
    }
}
