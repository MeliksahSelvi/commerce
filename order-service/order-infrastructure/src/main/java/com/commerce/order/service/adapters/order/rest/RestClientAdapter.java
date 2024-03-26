package com.commerce.order.service.adapters.order.rest;

import com.commerce.order.service.common.rest.client.CustomerHttpClient;
import com.commerce.order.service.order.port.rest.InnerRestPort;
import com.commerce.order.service.order.usecase.CustomerInfo;
import org.springframework.stereotype.Service;

/**
 * @Author mselvi
 * @Created 06.03.2024
 */

@Service
public class RestClientAdapter implements InnerRestPort {

    private final CustomerHttpClient client;

    public RestClientAdapter(CustomerHttpClient client) {
        this.client = client;
    }

    @Override
    public CustomerInfo getCustomerInfo(Long customerId) {
        return client.findById(customerId).getBody();
    }
}
