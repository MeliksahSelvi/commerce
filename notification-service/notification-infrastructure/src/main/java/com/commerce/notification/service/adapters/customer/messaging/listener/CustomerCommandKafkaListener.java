package com.commerce.notification.service.adapters.customer.messaging.listener;

import com.commerce.notification.service.common.exception.NotificationInfraException;
import com.commerce.notification.service.common.messaging.kafka.consumer.KafkaConsumer;
import com.commerce.notification.service.common.messaging.kafka.model.CustomerCommandKafkaModel;
import com.commerce.notification.service.customer.port.messaging.input.CustomerCommandMessageListener;
import com.commerce.notification.service.customer.usecase.CustomerInfo;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @Author mselvi
 * @Created 26.04.2024
 */

@Component
public class CustomerCommandKafkaListener implements KafkaConsumer {

    private static final Logger logger = LoggerFactory.getLogger(CustomerCommandKafkaListener.class);
    private final CustomerCommandMessageListener customerCommandMessageListener;
    private final ObjectMapper objectMapper;

    public CustomerCommandKafkaListener(CustomerCommandMessageListener customerCommandMessageListener, ObjectMapper objectMapper) {
        this.customerCommandMessageListener = customerCommandMessageListener;
        this.objectMapper = objectMapper;
    }

    @Override
    @KafkaListener(id = "${kafka-consumer-config.customer-command-consumer-group-id}",
            topics = "${notification-service.customer-command-topic-name}")
    public void receive(@Payload List<String> messages,
                        @Header(KafkaHeaders.RECEIVED_KEY) List<String> keys,
                        @Header(KafkaHeaders.RECEIVED_PARTITION) List<Integer> partitions,
                        @Header(KafkaHeaders.OFFSET) List<Long> offsets) {

        logger.info("{} number of messages received with keys:{}, partitions:{} and offsets:{}",
                messages.size(), keys, partitions, offsets);


        for (String message : messages) {
            try {
                CustomerCommandKafkaModel kafkaModel = exractDataFromJson(message);
                CustomerInfo customerInfo = buildUseCase(kafkaModel);
                customerCommandMessageListener.processMessage(customerInfo);
                logger.info("CustomerInfo sent for save action by email: {}", customerInfo.email());
            } catch (Exception e) {
                logger.error(e.getMessage());
            }
        }
    }

    private CustomerCommandKafkaModel exractDataFromJson(String strAsJson) {
        try {
            return objectMapper.readValue(strAsJson, CustomerCommandKafkaModel.class);
        } catch (JsonProcessingException e) {
            throw new NotificationInfraException("Could not read CustomerKafkaModel object", e);
        }
    }

    private CustomerInfo buildUseCase(CustomerCommandKafkaModel kafkaModel) {
        return new CustomerInfo(kafkaModel.id(), kafkaModel.firstName(), kafkaModel.lastName(), kafkaModel.email());
    }
}
