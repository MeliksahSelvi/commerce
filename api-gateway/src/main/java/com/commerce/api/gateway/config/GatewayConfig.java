package com.commerce.api.gateway.config;

import com.commerce.api.gateway.security.filter.AuthenticationFilter;
import io.github.resilience4j.circuitbreaker.CircuitBreakerConfig;
import io.github.resilience4j.timelimiter.TimeLimiterConfig;
import org.springframework.cloud.circuitbreaker.resilience4j.ReactiveResilience4JCircuitBreakerFactory;
import org.springframework.cloud.circuitbreaker.resilience4j.Resilience4JConfigBuilder;
import org.springframework.cloud.client.circuitbreaker.Customizer;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Duration;

/**
 * @Author mselvi
 * @Created 28.03.2024
 */

@Configuration
public class GatewayConfig {

    private final AuthenticationFilter authenticationFilter;
    private final GatewayConfigData gatewayServiceConfigData;

    public GatewayConfig(AuthenticationFilter authenticationFilter, GatewayConfigData gatewayServiceConfigData) {
        this.authenticationFilter = authenticationFilter;
        this.gatewayServiceConfigData = gatewayServiceConfigData;
    }

    @Bean
    Customizer<ReactiveResilience4JCircuitBreakerFactory> circuitBreakerFactoryCustomizer() {
        return reactiveResilience4JCircuitBreakerFactory ->
                reactiveResilience4JCircuitBreakerFactory.configureDefault(id -> new Resilience4JConfigBuilder(id)
                        .timeLimiterConfig(TimeLimiterConfig.custom()
                                .timeoutDuration(Duration.ofMillis(gatewayServiceConfigData.timeoutMs()))
                                .build())
                        .circuitBreakerConfig(CircuitBreakerConfig.custom()
                                .failureRateThreshold(gatewayServiceConfigData.failureRateThreshold())
                                .slowCallRateThreshold(gatewayServiceConfigData.slowCallRateThreshold())
                                .slowCallDurationThreshold(Duration.ofMillis(gatewayServiceConfigData.slowCallDurationThreshold()))
                                .permittedNumberOfCallsInHalfOpenState(gatewayServiceConfigData.permittedNumOfCallsInHalfOpenState())
                                .slidingWindowSize(gatewayServiceConfigData.slidingWindowSize())
                                .minimumNumberOfCalls(gatewayServiceConfigData.minNumberOfCalls())
                                .waitDurationInOpenState(Duration.ofMillis(gatewayServiceConfigData.waitDurationInOpenState()))
                                .build())
                        .build());

    }

    @Bean
    public RouteLocator routes(RouteLocatorBuilder builder) {
        return builder.routes()
                .route("user-service", r -> r.path("/user-service/**")
                        .filters(f -> f.filter(authenticationFilter))
                        .uri("lb://user-service"))
                .route("inventory-service", r -> r.path("/inventory-service/**")
                        .filters(f -> f.filter(authenticationFilter))
                        .uri("lb://inventory-service"))
                .route("order-service", r -> r.path("/order-service/**")
                        .filters(f -> f.filter(authenticationFilter))
                        .uri("lb://order-service"))
                .route("payment-service", r -> r.path("/payment-service/**")
                        .filters(f -> f.filter(authenticationFilter))
                        .uri("lb://payment-service"))
                .route("shipping-service", r -> r.path("/shipping-service/**")
                        .filters(f -> f.filter(authenticationFilter))
                        .uri("lb://shipping-service"))
                .build();
    }
}
