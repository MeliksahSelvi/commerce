package com.commerce.shipping.service.adapters.shipping.messaging.publisher;

import com.commerce.shipping.service.common.exception.ShippingInfraException;
import com.commerce.shipping.service.common.messaging.kafka.model.NotificationRequestKafkaModel;
import com.commerce.shipping.service.common.messaging.kafka.producer.KafkaProducerWithoutCallback;
import com.commerce.shipping.service.shipping.port.messaging.output.OrderNotificationMessagePublisher;
import com.commerce.shipping.service.shipping.usecase.OrderNotificationMessage;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @Author mselvi
 * @Created 09.03.2024
 */

@Component
public class OrderNotificationKafkaPublisher implements OrderNotificationMessagePublisher {

    private static final Logger logger = LoggerFactory.getLogger(OrderNotificationKafkaPublisher.class);
    private final KafkaProducerWithoutCallback kafkaProducer;
    private final ObjectMapper objectMapper;

    @Value("${shipping-service.notification-request-topic-name}")
    private String notificationTopicName;

    public OrderNotificationKafkaPublisher(KafkaProducerWithoutCallback kafkaProducer, ObjectMapper objectMapper) {
        this.kafkaProducer = kafkaProducer;
        this.objectMapper = objectMapper;
    }

    @Override
    public void publish(OrderNotificationMessage orderNotificationMessage) {
        Long orderId = orderNotificationMessage.orderId();
        logger.info("Received OrderNotificationMessage for order id: {} and customer id: {}", orderId);

        try {
            NotificationRequestKafkaModel kafkaModel = new NotificationRequestKafkaModel(orderNotificationMessage);
            kafkaProducer.send(notificationTopicName, orderId.toString(), convertDataToJson(kafkaModel));

            logger.info("NotificationAvroModel sent to Kafka for order id: {}", orderId);
        } catch (Exception e) {
            logger.error("Error while sending NotificationAvroModel to kafka with order id: {}  error: {}", orderId, e.getMessage());
        }
    }

    private String convertDataToJson(NotificationRequestKafkaModel userPrincipal) {
        try {
            return objectMapper.writeValueAsString(userPrincipal);
        } catch (JsonProcessingException e) {
            throw new ShippingInfraException("Could not create NotificationRequestKafkaModel object", e);
        }
    }
}
