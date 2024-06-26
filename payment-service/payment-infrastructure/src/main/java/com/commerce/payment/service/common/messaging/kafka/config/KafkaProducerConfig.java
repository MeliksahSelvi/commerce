package com.commerce.payment.service.common.messaging.kafka.config;

import org.apache.kafka.clients.producer.ProducerConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author mselvi
 * @Created 04.04.2024
 */

@Configuration
public class KafkaProducerConfig {

    private final KafkaConfigData kafkaConfigData;
    private final KafkaProducerConfigData kafkaProducerConfigData;

    public KafkaProducerConfig(KafkaConfigData kafkaConfigData, KafkaProducerConfigData kafkaProducerConfigData) {
        this.kafkaConfigData = kafkaConfigData;
        this.kafkaProducerConfigData = kafkaProducerConfigData;
    }

    @Bean
    public Map<String, Object> producerConfig() {
        Map<String, Object> props = new HashMap<>();
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaConfigData.bootstrapServers());
        props.put(kafkaConfigData.schemaRegistryUrlKey(), kafkaConfigData.schemaRegistryUrl());
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, kafkaProducerConfigData.keySerializerClass());
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, kafkaProducerConfigData.valueSerializerClass());
        props.put(ProducerConfig.BATCH_SIZE_CONFIG, kafkaProducerConfigData.batchSize() * kafkaProducerConfigData.batchSizeBoostFactor());
        props.put(ProducerConfig.LINGER_MS_CONFIG, kafkaProducerConfigData.lingerMs());
        props.put(ProducerConfig.COMPRESSION_TYPE_CONFIG, kafkaProducerConfigData.compressionType());
        props.put(ProducerConfig.ACKS_CONFIG, kafkaProducerConfigData.acks());
        props.put(ProducerConfig.REQUEST_TIMEOUT_MS_CONFIG, kafkaProducerConfigData.requestTimeoutMs());
        props.put(ProducerConfig.RETRIES_CONFIG, kafkaProducerConfigData.retryCount());
        return props;
    }

    @Bean
    public ProducerFactory<String, String> producerFactory() {
        return new DefaultKafkaProducerFactory<>(producerConfig());
    }

    @Bean
    public KafkaTemplate<String, String> kafkaTemplate() {
        return new KafkaTemplate<>(producerFactory());
    }
}