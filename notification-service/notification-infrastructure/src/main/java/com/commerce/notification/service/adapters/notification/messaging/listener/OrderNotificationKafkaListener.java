package com.commerce.notification.service.adapters.notification.messaging.listener;

import com.commerce.notification.service.common.exception.NotificationInfraException;
import com.commerce.notification.service.common.messaging.kafka.consumer.KafkaConsumer;
import com.commerce.notification.service.common.messaging.kafka.model.NotificationRequestKafkaModel;
import com.commerce.notification.service.common.valueobject.NotificationType;
import com.commerce.notification.service.notification.port.messaging.input.OrderNotificationMessageListener;
import com.commerce.notification.service.notification.usecase.OrderNotificationMessage;
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
 * @Created 08.03.2024
 */

@Component
public class OrderNotificationKafkaListener implements KafkaConsumer {

    private static final Logger logger = LoggerFactory.getLogger(OrderNotificationKafkaListener.class);
    private final OrderNotificationMessageListener orderNotificationMessageListener;
    private final ObjectMapper objectMapper;

    public OrderNotificationKafkaListener(OrderNotificationMessageListener orderNotificationMessageListener, ObjectMapper objectMapper) {
        this.orderNotificationMessageListener = orderNotificationMessageListener;
        this.objectMapper = objectMapper;
    }

    @Override
    @KafkaListener(id = "${kafka-consumer-config.order-notification-consumer-group-id}",
            topics = "${notification-service.notification-request-topic-name}")
    public void receive(@Payload List<String> messages,
                        @Header(KafkaHeaders.RECEIVED_KEY) List<String> keys,
                        @Header(KafkaHeaders.RECEIVED_PARTITION) List<Integer> partitions,
                        @Header(KafkaHeaders.OFFSET) List<Long> offsets) {

        logger.info("{} number of messages received with keys:{}, partitions:{} and offsets:{}",
                messages.size(), keys, partitions, offsets);

        for (String message : messages) {
            try {
                NotificationRequestKafkaModel kafkaModel = exractDataFromJson(message);
                OrderNotificationMessage orderNotificationMessage = buildUseCase(kafkaModel);
                logger.info("Notification sending {} action for orderId: {}", orderNotificationMessage.notificationType(), orderNotificationMessage.orderId());
                orderNotificationMessageListener.processMessage(orderNotificationMessage);
            } catch (Exception e) {
                logger.error(e.getMessage());
            }
        }
    }

    private NotificationRequestKafkaModel exractDataFromJson(String strAsJson) {
        try {
            return objectMapper.readValue(strAsJson, NotificationRequestKafkaModel.class);
        } catch (JsonProcessingException e) {
            throw new NotificationInfraException("Could not read NotificationRequestKafkaModel object", e);
        }
    }

    private OrderNotificationMessage buildUseCase(NotificationRequestKafkaModel kafkaModel) {
        return new OrderNotificationMessage(kafkaModel.orderId(), kafkaModel.customerId(),
                NotificationType.valueOf(kafkaModel.notificationType().name()), kafkaModel.message());
    }
}
