package com.commerce.order.service.common.rest.handler;

import com.commerce.order.service.common.exception.*;
import com.commerce.order.service.common.rest.dto.ErrorResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author mselvi
 * @Created 01.03.2024
 */

@ControllerAdvice
public class OrderExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(OrderExceptionHandler.class);

    @ResponseBody
    @ExceptionHandler(value = OrderDomainException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleException(OrderDomainException exception) {
        logger.error(exception.getMessage(), exception);
        return buildErrorResponse(HttpStatus.BAD_REQUEST, exception.getMessage());
    }

    @ResponseBody
    @ExceptionHandler(value = OrderNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse handleException(OrderNotFoundException exception) {
        logger.error(exception.getMessage(), exception);
        return buildErrorResponse(HttpStatus.NOT_FOUND, exception.getMessage());
    }

    @ResponseBody
    @ExceptionHandler(value = InventoryOutboxNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse handleException(InventoryOutboxNotFoundException exception) {
        logger.error(exception.getMessage(), exception);
        return buildErrorResponse(HttpStatus.NOT_FOUND, exception.getMessage());
    }

    @ResponseBody
    @ExceptionHandler(value = PaymentOutboxNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse handleException(PaymentOutboxNotFoundException exception) {
        logger.error(exception.getMessage(), exception);
        return buildErrorResponse(HttpStatus.NOT_FOUND, exception.getMessage());
    }

    @ResponseBody
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ErrorResponse handleValidationErrors(MethodArgumentNotValidException exception) {
        List<String> errors = exception.getBindingResult().getFieldErrors().stream()
                .map(FieldError::getDefaultMessage)
                .toList();
        String errorMessage = String.join(",", errors);
        logger.error(errorMessage, exception);
        return buildErrorResponse(HttpStatus.BAD_REQUEST, errorMessage);
    }

    @ResponseBody
    @ExceptionHandler(value = OrderInfraException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorResponse handleException(OrderInfraException exception) {
        logger.error(exception.getMessage(), exception);
        return buildErrorResponse(HttpStatus.BAD_REQUEST, exception.getMessage());
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
