package com.commerce.inventory.service.common.messaging.kafka.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @Author mselvi
 * @Created 07.03.2024
 */

@ConfigurationProperties(prefix = "kafka-producer-config")
public record KafkaProducerConfigData(String keySerializerClass, String valueSerializerClass, String compressionType, String acks,
                                      Integer batchSize, Integer batchSizeBoostFactor, Integer lingerMs, Integer requestTimeoutMs, Integer retryCount) {
}
