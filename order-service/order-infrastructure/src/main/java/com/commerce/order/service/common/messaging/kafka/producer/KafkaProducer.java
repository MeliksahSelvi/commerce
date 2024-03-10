package com.commerce.order.service.common.messaging.kafka.producer;

import com.commerce.order.service.common.messaging.kafka.model.KafkaPayload;
import org.springframework.kafka.support.SendResult;

import java.io.Serializable;
import java.util.function.BiConsumer;

/**
 * @Author mselvi
 * @Created 06.03.2024
 */

public interface KafkaProducer<K extends Serializable, V extends KafkaPayload> {

    void send(String topicName, K key, V message, BiConsumer<SendResult<K, V>, Throwable> callback);
}
