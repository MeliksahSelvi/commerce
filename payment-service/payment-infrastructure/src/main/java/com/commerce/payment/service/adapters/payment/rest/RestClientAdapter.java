package com.commerce.payment.service.adapters.payment.rest;

import com.commerce.payment.service.common.rest.client.CustomerHttpClient;
import com.commerce.payment.service.payment.port.rest.RestPort;
import org.springframework.stereotype.Service;

/**
 * @Author mselvi
 * @Created 14.03.2024
 */

@Service
public class RestClientAdapter implements RestPort {

    private final CustomerHttpClient client;

    public RestClientAdapter(CustomerHttpClient client) {
        this.client = client;
    }

    @Override
    public boolean isCustomerExist(Long customerId) {
        return client.isCustomerExist(customerId);
    }
}
