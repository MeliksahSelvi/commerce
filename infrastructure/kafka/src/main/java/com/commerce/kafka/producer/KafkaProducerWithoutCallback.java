package com.commerce.kafka.producer;

import org.apache.avro.specific.SpecificRecordBase;

import java.io.Serializable;

/**
 * @Author mselvi
 * @Created 18.03.2024
 */

public interface KafkaProducerWithoutCallback<K extends Serializable, V extends SpecificRecordBase> {

    void send(String topicName, K key, V message);
}

