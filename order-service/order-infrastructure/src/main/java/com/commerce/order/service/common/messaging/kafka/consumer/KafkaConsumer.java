package com.commerce.order.service.common.messaging.kafka.consumer;

import com.commerce.order.service.common.messaging.kafka.model.KafkaPayload;

import java.util.List;

/**
 * @Author mselvi
 * @Created 06.03.2024
 */

public interface KafkaConsumer<T extends KafkaPayload> {
    void receive(List<T> messages, List<String> keys, List<Integer> partitions, List<Long> offsets);
}
