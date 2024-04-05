package com.commerce.inventory.service.common.messaging.kafka.helper;

import com.commerce.inventory.service.common.exception.InventoryDomainException;
import com.commerce.inventory.service.common.outbox.OutboxStatus;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;

import java.util.function.BiConsumer;

/**
 * @Author mselvi
 * @Created 07.03.2024
 */

@Component
public class KafkaHelper {

    private static final Logger logger = LoggerFactory.getLogger(KafkaHelper.class);
    private final ObjectMapper objectMapper;

    public KafkaHelper(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    public <T> T getPayload(String payload, Class<T> outputType) {
        try {
            return objectMapper.readValue(payload, outputType);
        } catch (JsonProcessingException e) {
            throw new InventoryDomainException(String.format("Could not read %s object! %s", outputType.getName(), e.getMessage()));
        }
    }

    public <T, U> BiConsumer<SendResult<String, String>, Throwable> getKafkaCallback(T kafkaModel, U outboxMessage,
                                                                                     BiConsumer<U, OutboxStatus> outboxCallback, Long orderId) {
        return (result, ex) -> {
            if (ex == null) {
                logger.info("Received successful response from Kafka for order id: {}", orderId);
                outboxCallback.accept(outboxMessage, OutboxStatus.COMPLETED);
            } else {
                logger.error("Error while sending {} with message: {} and outbox type: {}",
                        kafkaModel.getClass().getSimpleName(), kafkaModel, outboxMessage.getClass().getName(), ex);
                outboxCallback.accept(outboxMessage, OutboxStatus.FAILED);
            }
        };
    }
}
