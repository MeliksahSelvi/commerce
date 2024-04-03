package com.commerce.api.gateway.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @Author mselvi
 * @Created 28.03.2024
 */

@ConfigurationProperties(prefix = "gateway-config")
public record GatewayConfigData(Long timeoutMs, Float failureRateThreshold, Float slowCallRateThreshold,
                                Long slowCallDurationThreshold, Integer permittedNumOfCallsInHalfOpenState,
                                Integer slidingWindowSize, Integer minNumberOfCalls, Long waitDurationInOpenState) {
}
