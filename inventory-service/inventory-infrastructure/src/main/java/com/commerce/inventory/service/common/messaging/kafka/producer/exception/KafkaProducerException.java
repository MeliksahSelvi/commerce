package com.commerce.inventory.service.common.messaging.kafka.producer.exception;

/**
 * @Author mselvi
 * @Created 07.03.2024
 */

public class KafkaProducerException extends RuntimeException{
    public KafkaProducerException(String message) {
        super(message);
    }
}
