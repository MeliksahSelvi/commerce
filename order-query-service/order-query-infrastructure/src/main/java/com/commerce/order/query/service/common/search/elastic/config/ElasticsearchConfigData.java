package com.commerce.order.query.service.common.search.elastic.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @Author mselvi
 * @Created 27.04.2024
 */

@ConfigurationProperties(prefix = "elastic-config")
public record ElasticsearchConfigData(String connectionUrl, Integer connectionTimeoutMs,
                                      Integer socketTimeoutMs) {
}
