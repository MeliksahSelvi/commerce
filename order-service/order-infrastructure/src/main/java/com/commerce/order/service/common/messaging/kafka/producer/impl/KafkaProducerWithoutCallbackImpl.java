package com.commerce.order.service.common.messaging.kafka.producer.impl;

import com.commerce.order.service.common.messaging.kafka.exception.KafkaProducerException;
import com.commerce.order.service.common.messaging.kafka.producer.KafkaProducerWithoutCallback;
import jakarta.annotation.PreDestroy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.KafkaException;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

/**
 * @Author mselvi
 * @Created 04.04.2024
 */

@Component
public class KafkaProducerWithoutCallbackImpl implements KafkaProducerWithoutCallback {

    private static final Logger logger = LoggerFactory.getLogger(KafkaProducerWithoutCallbackImpl.class);
    private final KafkaTemplate<String, String> kafkaTemplate;

    public KafkaProducerWithoutCallbackImpl(KafkaTemplate<String, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    @Override
    public void send(String topicName, String key, String message) {
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
