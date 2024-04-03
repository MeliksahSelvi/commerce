package com.commerce.payment.service.common.rest.handler;

import com.commerce.payment.service.common.exception.AccountNotFoundException;
import com.commerce.payment.service.common.exception.PaymentDomainException;
import com.commerce.payment.service.common.exception.PaymentInfraException;
import com.commerce.payment.service.common.exception.PaymentNotFoundException;
import com.commerce.payment.service.common.rest.dto.ErrorResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.List;

/**
 * @Author mselvi
 * @Created 12.03.2024
 */

@ControllerAdvice
public class PaymentExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(PaymentExceptionHandler.class);

    @ResponseBody
    @ExceptionHandler(value = PaymentDomainException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleException(PaymentDomainException exception) {
        logger.error(exception.getMessage(), exception);
        return buildErrorResponse(HttpStatus.BAD_REQUEST, exception.getMessage());
    }

    @ResponseBody
    @ExceptionHandler(value = PaymentNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse handleException(PaymentNotFoundException exception) {
        logger.error(exception.getMessage(), exception);
        return buildErrorResponse(HttpStatus.NOT_FOUND, exception.getMessage());
    }

    @ResponseBody
    @ExceptionHandler(value = AccountNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse handleException(AccountNotFoundException exception) {
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
    @ExceptionHandler(value = PaymentInfraException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorResponse handleException(PaymentInfraException exception) {
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
