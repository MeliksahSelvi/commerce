package com.commerce.api.gateway.security.filter;

import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**
 * @Author mselvi
 * @Created 28.03.2024
 */

@Component
public class AuthenticationFilter implements GatewayFilter {

    private final RouterValidator routerValidator;
    private final JwtTokenValidator jwtTokenValidator;

    public AuthenticationFilter(RouterValidator routerValidator, JwtTokenValidator jwtTokenValidator) {
        this.routerValidator = routerValidator;
        this.jwtTokenValidator = jwtTokenValidator;
    }


    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();

        if (routerValidator.isSecured.test(request)) {
            if (this.isAuthMissing(request)) {
                return this.onError(exchange, HttpStatus.UNAUTHORIZED);
            }

            final String token = this.getAuthHeader(request);

            String userPrincipalAsJson = jwtTokenValidator.getSubjectFromToken(token);
            if (jwtTokenValidator.isNotExistOnCache(token,userPrincipalAsJson)) {
                return this.onError(exchange, HttpStatus.FORBIDDEN);
            }

            this.updateRequest(exchange, userPrincipalAsJson);
        }
        return chain.filter(exchange);
    }

    private boolean isAuthMissing(ServerHttpRequest request) {
        return !request.getHeaders().containsKey("Authorization");
    }

    private Mono<Void> onError(ServerWebExchange exchange, HttpStatus httpStatus) {
        ServerHttpResponse response = exchange.getResponse();
        response.setStatusCode(httpStatus);
        return response.setComplete();
    }

    private String getAuthHeader(ServerHttpRequest request) {
        String fullToken = request.getHeaders().getOrEmpty("Authorization").get(0);

        String token = null;
        if (StringUtils.hasText(fullToken)) {
            String bearerStr = "Bearer ";

            if (fullToken.startsWith(bearerStr)) {
                token = fullToken.substring(bearerStr.length());
            }
        }
        return token;
    }

    private void updateRequest(ServerWebExchange exchange, String userPrincipalAsJson) {
        exchange.getRequest().mutate().header("userPrincipal", userPrincipalAsJson).build();
    }
}
