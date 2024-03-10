package com.commerce.order.service.common.messaging.kafka.producer;

import com.commerce.order.service.common.messaging.kafka.model.KafkaPayload;

import java.io.Serializable;

/**
 * @Author mselvi
 * @Created 08.03.2024
 */

public interface KafkaProducerWithoutCallback<K extends Serializable, V extends KafkaPayload> {

    void send(String topicName, K key, V message);
}
