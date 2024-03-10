package com.commerce.shipping.service.common.messaging.kafka.producer.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @Author mselvi
 * @Created 09.03.2024
 */

@ConfigurationProperties(prefix = "kafka-producer-config")
public record KafkaProducerConfigData(String keySerializerClass, String valueSerializerClass, String compressionType, String acks,
                                      Integer batchSize, Integer batchSizeBoostFactor, Integer lingerMs, Integer requestTimeoutMs, Integer retryCount) {
}
