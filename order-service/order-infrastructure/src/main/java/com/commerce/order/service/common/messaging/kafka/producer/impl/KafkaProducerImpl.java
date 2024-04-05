package com.commerce.order.service.common.messaging.kafka.producer.impl;

import com.commerce.order.service.common.messaging.kafka.exception.KafkaProducerException;
import com.commerce.order.service.common.messaging.kafka.producer.KafkaProducer;
import jakarta.annotation.PreDestroy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.KafkaException;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;

import java.util.concurrent.CompletableFuture;
import java.util.function.BiConsumer;

/**
 * @Author mselvi
 * @Created 04.04.2024
 */

@Component
public class KafkaProducerImpl implements KafkaProducer {

    private static final Logger logger = LoggerFactory.getLogger(KafkaProducerImpl.class);
    private final KafkaTemplate<String, String> kafkaTemplate;

    public KafkaProducerImpl(KafkaTemplate<String, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    @Override
    public void send(String topicName, String key, String message, BiConsumer<SendResult<String, String>, Throwable> callback) {
        logger.info("Sending message= {} to topic= {}", message, topicName);
        try {
            CompletableFuture<SendResult<String, String>> kafkaResult = kafkaTemplate.send(topicName, key, message);
            kafkaResult.whenComplete(callback);
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
