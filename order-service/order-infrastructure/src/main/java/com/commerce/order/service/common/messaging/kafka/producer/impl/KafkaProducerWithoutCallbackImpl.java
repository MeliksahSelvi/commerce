package com.commerce.order.service.common.messaging.kafka.producer.impl;

import com.commerce.order.service.common.messaging.kafka.model.KafkaPayload;
import com.commerce.order.service.common.messaging.kafka.producer.KafkaProducerWithoutCallback;
import com.commerce.order.service.common.messaging.kafka.producer.exception.KafkaProducerException;
import jakarta.annotation.PreDestroy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.KafkaException;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import java.io.Serializable;

/**
 * @Author mselvi
 * @Created 08.03.2024
 */

@Component
public class KafkaProducerWithoutCallbackImpl<K extends Serializable, V extends KafkaPayload> implements KafkaProducerWithoutCallback<K, V> {

    private static final Logger logger = LoggerFactory.getLogger(KafkaProducerWithoutCallbackImpl.class);
    private final KafkaTemplate<K, V> kafkaTemplate;

    public KafkaProducerWithoutCallbackImpl(KafkaTemplate<K, V> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    @Override
    public void send(String topicName, K key, V message) {
        logger.info("Sending message= {} to topic= {}", message, topicName);
        try {
            kafkaTemplate.send(topicName, key, message);
        } catch (KafkaException e) {
            logger.error("Error on kafka producer with key: {}, message: {} and exception: {}", key, message, e.getMessage());
            throw new KafkaProducerException("Error on kafka producer with key: " + key + " and message: " + message);
        }
    }

    @PreDestroy
    public void close() {
        if (kafkaTemplate != null) {
            logger.info("Kafka producer is destroying!");
            kafkaTemplate.destroy();
        }
    }
}
