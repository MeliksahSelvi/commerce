package com.commerce.order.service.common.rest.client;

import com.commerce.order.service.order.usecase.CustomerResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.service.annotation.GetExchange;

/**
 * @Author mselvi
 * @Created 19.03.2024
 */

public interface CustomerHttpClient {

    @GetExchange("/customer-service/api/v1/customers/{id}")
    ResponseEntity<CustomerResponse> findById(@PathVariable Long id);
}
