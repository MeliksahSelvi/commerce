package com.commerce.order.query.service.common.messaging.kafka.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @Author mselvi
 * @Created 27.04.2024
 */

@ConfigurationProperties(prefix = "kafka-config")
public record KafkaConfigData(String bootstrapServers, String schemaRegistryUrlKey, String schemaRegistryUrl,
                              Integer numOfPartitions, Short replicationFactor) {
}
