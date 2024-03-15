package com.commerce.shipping.service.common.messaging.kafka.producer.exception;

/**
 * @Author mselvi
 * @Created 09.03.2024
 */

public class KafkaProducerException extends RuntimeException{//todo maybe handle

    public KafkaProducerException(String message) {
        super(message);
    }
}
