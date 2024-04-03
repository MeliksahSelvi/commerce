package com.commerce.notification.service.common.rest.config;

import com.commerce.notification.service.common.exception.NotificationInfraException;
import com.commerce.notification.service.common.model.UserPrincipal;
import com.commerce.notification.service.common.rest.client.CustomerHttpClient;
import com.commerce.notification.service.common.rest.handler.RestClientResponseErrorHandler;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
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

    private final ObjectMapper objectMapper;

    public RestClientConfig(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @Bean
    ResponseErrorHandler handler() {
        return new RestClientResponseErrorHandler();
    }

    @Bean
    RestClient restClient(RestClient.Builder builder) {
        UserPrincipal userPrincipal = new UserPrincipal("meliksah.selvi2834@gmail.com", 1L, 1L);//todo fix
        return builder.baseUrl("http://localhost:8086")
//        return builder.baseUrl("http://customer-service")//todo fix
                .defaultStatusHandler(handler())
                .defaultHeader("userPrincipal", convertDataToJson(userPrincipal))
                .build();
    }

    @Bean
    CustomerHttpClient customerHttpClient(RestClient restClient) {
        HttpServiceProxyFactory factory = HttpServiceProxyFactory.builderFor(RestClientAdapter.create(restClient)).build();
        return factory.createClient(CustomerHttpClient.class);
    }

    private String convertDataToJson(UserPrincipal userPrincipal) {
        try {
            return objectMapper.writeValueAsString(userPrincipal);
        } catch (JsonProcessingException e) {
            throw new NotificationInfraException("Could not create UserPrincipal object", e);
        }
    }
}
