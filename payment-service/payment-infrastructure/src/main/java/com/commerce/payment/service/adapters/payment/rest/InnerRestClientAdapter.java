package com.commerce.payment.service.adapters.payment.rest;

import com.commerce.payment.service.common.rest.client.CustomerHttpClient;
import com.commerce.payment.service.payment.port.rest.InnerRestPort;
import com.commerce.payment.service.payment.usecase.CustomerResponse;
import org.springframework.stereotype.Service;

/**
 * @Author mselvi
 * @Created 14.03.2024
 */

@Service
public class InnerRestClientAdapter implements InnerRestPort {

    private final CustomerHttpClient client;

    public InnerRestClientAdapter(CustomerHttpClient client) {
        this.client = client;
    }

    @Override
    public CustomerResponse getCustomerInfo(Long customerId) {
        return client.findById(customerId).getBody();
    }
}
