package com.commerce.notification.service.common.messaging.kafka.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @Author mselvi
 * @Created 08.03.2024
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
