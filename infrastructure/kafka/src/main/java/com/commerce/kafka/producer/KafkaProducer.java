package com.commerce.kafka.producer;

import org.apache.avro.specific.SpecificRecordBase;
import org.springframework.kafka.support.SendResult;

import java.io.Serializable;
import java.util.function.BiConsumer;

/**
 * @Author mselvi
 * @Created 18.03.2024
 */

public interface KafkaProducer<K extends Serializable, V extends SpecificRecordBase> {

    void send(String topicName, K key, V message, BiConsumer<SendResult<K, V>, Throwable> callback);
}
