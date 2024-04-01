package com.commerce.order.service.common.rest.config;

import com.commerce.order.service.common.rest.client.CustomerHttpClient;
import com.commerce.order.service.common.rest.handler.RestClientResponseErrorHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.ResponseErrorHandler;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.support.RestClientAdapter;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;

/**
 * @Author mselvi
 * @Created 19.03.2024
 */

@Configuration
public class RestClientConfig {

    @Bean
    ResponseErrorHandler handler() {
        return new RestClientResponseErrorHandler();
    }

    @Bean
    RestClient restClient(RestClient.Builder builder) {
        return builder.baseUrl("http://localhost:8085")
//        return builder.baseUrl("http://customer-service")//todo fix
                .defaultStatusHandler(handler())
                .build();
    }

    @Bean
    CustomerHttpClient customerHttpClient(RestClient restClient) {
        HttpServiceProxyFactory factory = HttpServiceProxyFactory.builderFor(RestClientAdapter.create(restClient)).build();
        return factory.createClient(CustomerHttpClient.class);
    }
}
