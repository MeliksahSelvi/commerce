package com.commerce.payment.service.common.messaging.kafka.exception;

/**
 * @Author mselvi
 * @Created 04.04.2024
 */

public class KafkaProducerException extends RuntimeException{
    public KafkaProducerException(String message) {
        super(message);
    }
}
