package com.commerce.shipping.service.common.messaging.kafka.producer;

/**
 * @Author mselvi
 * @Created 04.04.2024
 */

public interface KafkaProducerWithoutCallback {

    void send(String topicName, String key, String message);
}
