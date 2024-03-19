package com.commerce.notification.service.common.rest.client;

import com.commerce.notification.service.notification.usecase.CustomerResponse;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.service.annotation.GetExchange;

/**
 * @Author mselvi
 * @Created 19.03.2024
 */

public interface CustomerHttpClient {

    @GetExchange("/customer-service/api/v1/customers/{id}")
    CustomerResponse findById(@PathVariable Long id);
}