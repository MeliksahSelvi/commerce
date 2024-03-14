package com.commerce.notification.service.common.messaging.kafka.consumer.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @Author mselvi
 * @Created 08.03.2024
 */

@ConfigurationProperties(prefix = "kafka-consumer-config")
public record KafkaConsumerConfigData(String keyDeserializer, String valueDeserializer, String autoOffsetReset,
                                      Boolean batchListener, Boolean autoStartup, Integer concurrencyLevel,
                                      Integer sessionTimeoutMs, Integer heartbeatIntervalMs, Integer maxPollIntervalMs,
                                      Long pollTimeoutMs, Integer maxPollRecords,
                                      Integer maxPartitionFetchBytesDefault,
                                      Integer maxPartitionFetchBytesBoostFactor) {
}
