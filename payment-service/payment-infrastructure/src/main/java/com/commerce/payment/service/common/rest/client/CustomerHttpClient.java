package com.commerce.payment.service.common.rest.client;

import com.commerce.payment.service.payment.usecase.CustomerInfo;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.service.annotation.GetExchange;

/**
 * @Author mselvi
 * @Created 19.03.2024
 */

public interface CustomerHttpClient {

    @GetExchange("/customer-service/api/v1/customers/{id}")
    ResponseEntity<CustomerInfo> findById(@PathVariable Long id);
}
