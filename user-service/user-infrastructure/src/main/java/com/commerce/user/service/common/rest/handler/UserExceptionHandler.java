package com.commerce.user.service.common.rest.handler;

import com.commerce.user.service.common.exception.UserDomainException;
import com.commerce.user.service.common.exception.UserNotFoundException;
import com.commerce.user.service.common.rest.dto.ErrorResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * @Author mselvi
 * @Created 31.03.2024
 */

@ControllerAdvice
public class UserExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(UserExceptionHandler.class);

    @ResponseBody
    @ExceptionHandler(value = UserDomainException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleException(UserDomainException exception) {
        logger.error(exception.getMessage(), exception);
        return buildErrorResponse(HttpStatus.BAD_REQUEST, exception.getMessage());
    }

    @ResponseBody
    @ExceptionHandler(value = UserNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse handleException(UserNotFoundException exception) {
        logger.error(exception.getMessage(), exception);
        return buildErrorResponse(HttpStatus.NOT_FOUND, exception.getMessage());
    }

    @ResponseBody
    @ExceptionHandler(value = Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorResponse handleException(Exception exception) {
        logger.error(exception.getMessage(), exception);
        return buildErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, exception.getMessage());
    }

    private ErrorResponse buildErrorResponse(HttpStatus httpStatus, String message) {
        return new ErrorResponse(httpStatus.getReasonPhrase(), message);
    }
}
