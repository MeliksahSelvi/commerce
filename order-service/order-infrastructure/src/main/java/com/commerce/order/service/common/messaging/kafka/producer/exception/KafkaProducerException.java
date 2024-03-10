package com.commerce.order.service.common.messaging.kafka.producer.exception;

/**
 * @Author mselvi
 * @Created 06.03.2024
 */

public class KafkaProducerException extends RuntimeException{
    public KafkaProducerException(String message) {
        super(message);
    }
}
