package com.commerce.payment.service.common.messaging.kafka.producer;

/**
 * @Author mselvi
 * @Created 26.04.2024
 */

public interface KafkaProducerWithoutCallback {

    void send(String topicName, String key, String message);
}
