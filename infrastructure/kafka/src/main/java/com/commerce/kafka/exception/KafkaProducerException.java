package com.commerce.kafka.exception;

/**
 * @Author mselvi
 * @Created 18.03.2024
 */

public class KafkaProducerException extends RuntimeException{
    public KafkaProducerException(String message) {
        super(message);
    }
}
