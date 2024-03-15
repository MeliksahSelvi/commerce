package com.commerce.notification.service.adapters.notification.rest;

import com.commerce.notification.service.notification.port.rest.RestPort;
import com.commerce.notification.service.notification.usecase.CustomerResponse;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

/**
 * @Author mselvi
 * @Created 14.03.2024
 */

@Service
public class RestClientAdapter implements RestPort {

    private final RestClient restClient;

    public RestClientAdapter() {
        this.restClient = RestClient.builder()
//                .baseUrl("localhost:8080")//todo api gateway url and dont add this part to suburls
                .build();
    }

    @Override
    public CustomerResponse getCustomerInfo(Long customerId) {
        return restClient.get()
                .uri("http://localhost:8085/customer-service/api/v1/customers/{id}",customerId)
                .retrieve()
                .body(CustomerResponse.class);
    }
}
