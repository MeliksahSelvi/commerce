package com.commerce.shipping.service.common.messaging.kafka.helper;

/**
 * @Author mselvi
 * @Created 09.03.2024
 */

public class KafkaProducerException extends RuntimeException{//todo maybe handle

    public KafkaProducerException(String message) {
        super(message);
    }
}
