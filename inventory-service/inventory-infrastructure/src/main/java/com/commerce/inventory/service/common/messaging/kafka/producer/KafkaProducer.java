package com.commerce.inventory.service.common.messaging.kafka.producer;

import org.springframework.kafka.support.SendResult;

import java.util.function.BiConsumer;

/**
 * @Author mselvi
 * @Created 04.04.2024
 */

public interface KafkaProducer {

    void send(String topicName, String key, String message, BiConsumer<SendResult<String, String>, Throwable> callback);
}
