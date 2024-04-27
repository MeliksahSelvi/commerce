package com.commerce.api.gateway.security.filter;

import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.function.Predicate;

/**
 * @Author mselvi
 * @Created 28.03.2024
 */

@Component
public class RouterValidator {

    private static final List<String> openApiEndpoints = List.of("/api/v1/auth/login", "/api/v1/auth/register","/api/v1/roles", "/swagger-ui/**",
            "/swagger-ui.html", "/v3/api-docs/**"
    );


    public Predicate<ServerHttpRequest> isSecured =
            request -> openApiEndpoints.stream()
                    .noneMatch(uri -> request.getURI().getPath().contains(uri));
}
