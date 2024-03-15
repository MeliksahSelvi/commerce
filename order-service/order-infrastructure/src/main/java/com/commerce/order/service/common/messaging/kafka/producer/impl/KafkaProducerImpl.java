package com.commerce.order.service.common.messaging.kafka.producer.impl;

import com.commerce.order.service.common.messaging.kafka.producer.KafkaProducer;
import com.commerce.order.service.common.messaging.kafka.exception.KafkaProducerException;
import jakarta.annotation.PreDestroy;
import org.apache.avro.specific.SpecificRecordBase;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.KafkaException;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.concurrent.CompletableFuture;
import java.util.function.BiConsumer;

/**
 * @Author mselvi
 * @Created 06.03.2024
 */

@Component
public class KafkaProducerImpl<K extends Serializable, V extends SpecificRecordBase> implements KafkaProducer<K, V> {

    private static final Logger logger = LoggerFactory.getLogger(KafkaProducerImpl.class);
    private final KafkaTemplate<K, V> kafkaTemplate;

    public KafkaProducerImpl(KafkaTemplate<K, V> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    @Override
    public void send(String topicName, K key, V message, BiConsumer<SendResult<K, V>, Throwable> callback) {
        logger.info("Sending message= {} to topic= {}", message, topicName);
        try {
            CompletableFuture<SendResult<K, V>> kafkaResult = kafkaTemplate.send(topicName, key, message);
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
