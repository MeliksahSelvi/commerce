package com.commerce.notification.service.adapters.notification.rest;

import com.commerce.notification.service.common.rest.client.CustomerHttpClient;
import com.commerce.notification.service.notification.port.rest.InnerRestPort;
import com.commerce.notification.service.notification.usecase.CustomerInfo;
import org.springframework.stereotype.Service;

/**
 * @Author mselvi
 * @Created 14.03.2024
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
