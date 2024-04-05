package com.commerce.inventory.service.common.messaging.kafka.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @Author mselvi
 * @Created 04.04.2024
 */

@ConfigurationProperties(prefix = "kafka-consumer-config")
public record KafkaConsumerConfigData(String keyDeserializer, String valueDeserializer, String autoOffsetReset,
                                      String specificAvroReaderKey, String specificAvroReader,
                                      Boolean batchListener, Boolean autoStartup, Integer concurrencyLevel,
                                      Integer sessionTimeoutMs, Integer heartbeatIntervalMs, Integer maxPollIntervalMs,
                                      Long pollTimeoutMs, Integer maxPollRecords,
                                      Integer maxPartitionFetchBytesDefault,
                                      Integer maxPartitionFetchBytesBoostFactor) {
}
