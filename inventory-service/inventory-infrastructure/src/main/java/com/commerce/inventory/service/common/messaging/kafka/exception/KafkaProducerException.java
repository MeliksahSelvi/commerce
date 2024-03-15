package com.commerce.inventory.service.common.messaging.kafka.exception;

/**
 * @Author mselvi
 * @Created 07.03.2024
 */

public class KafkaProducerException extends RuntimeException{//todo maybe handle
    public KafkaProducerException(String message) {
        super(message);
    }
}
