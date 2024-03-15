package com.commerce.order.service.common.messaging.kafka.exception;

/**
 * @Author mselvi
 * @Created 06.03.2024
 */

public class KafkaProducerException extends RuntimeException{//todo maybe handle
    public KafkaProducerException(String message) {
        super(message);
    }
}
