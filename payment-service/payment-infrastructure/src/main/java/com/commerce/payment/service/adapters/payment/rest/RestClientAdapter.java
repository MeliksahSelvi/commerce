package com.commerce.payment.service.adapters.payment.rest;

import com.commerce.payment.service.payment.port.rest.RestPort;
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
    public boolean isExistCustomer(Long customerId) {
        return restClient.get()
                .uri("http://localhost:8085/customer-service/api/v1/customers/exist/{id}",customerId)//todo retrieve uri from config files
                .retrieve()
                .body(Boolean.class);
    }
}
