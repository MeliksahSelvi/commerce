package com.commerce.order.service.common.rest.config;

import com.commerce.order.service.common.exception.OrderInfraException;
import com.commerce.order.service.common.model.UserPrincipal;
import com.commerce.order.service.common.rest.client.CustomerHttpClient;
import com.commerce.order.service.common.rest.handler.RestClientResponseErrorHandler;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserPrincipal userPrincipal;
        if (authentication != null) {
            userPrincipal = (UserPrincipal) authentication.getPrincipal();
        } else {
            userPrincipal = new UserPrincipal("meliksah.selvi2834@gmail.com", 1L, 1L);//todo fix
        }
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
            throw new OrderInfraException("Could not create UserPrincipal object", e);
        }
    }
}
