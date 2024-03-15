package com.commerce.shipping.service.common.messaging.kafka.producer;

import org.apache.avro.specific.SpecificRecordBase;

import java.io.Serializable;

/**
 * @Author mselvi
 * @Created 09.03.2024
 */

public interface KafkaProducerWithoutCallback<K extends Serializable, V extends SpecificRecordBase> {

    void send(String topicName, K key, V message);
}
