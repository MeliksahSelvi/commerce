package com.commerce.order.query.service.common.messaging.kafka.consumer;

import java.util.List;

/**
 * @Author mselvi
 * @Created 27.04.2024
 */

public interface KafkaConsumer {
    void receive(List<String> messages, List<String> keys, List<Integer> partitions, List<Long> offsets);
}
