package com.commerce.payment.service.common.rest.handler;

import com.commerce.payment.service.common.exception.PaymentDomainException;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.client.ResponseErrorHandler;

import java.io.IOException;

/**
 * @Author mselvi
 * @Created 19.03.2024
 */

@Component
public class RestClientResponseErrorHandler implements ResponseErrorHandler {


    @Override
    public boolean hasError(ClientHttpResponse response) throws IOException {
        return (response.getStatusCode().is4xxClientError() || response.getStatusCode().is5xxServerError());
    }

    @Override
    public void handleError(ClientHttpResponse response) throws IOException {

        HttpStatusCode responseStatusCode = response.getStatusCode();
        String statusText = response.getStatusText();
        if (responseStatusCode.is5xxServerError()) {
            throw new RuntimeException();
        } else if (responseStatusCode.is4xxClientError()) {
            if (responseStatusCode.isSameCodeAs(HttpStatus.BAD_REQUEST)) {
                throw new PaymentDomainException(statusText);
            } else if (responseStatusCode.isSameCodeAs(HttpStatus.NOT_FOUND)) {
                throw new PaymentDomainException(statusText);
            }
        }
    }
}
