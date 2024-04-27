package com.commerce.shipping.service.adapters.shipping.messaging.listener;

import com.commerce.shipping.service.common.exception.ShippingInfraException;
import com.commerce.shipping.service.common.messaging.kafka.consumer.KafkaConsumer;
import com.commerce.shipping.service.common.messaging.kafka.model.NotificationRequestKafkaModel;
import com.commerce.shipping.service.common.valueobject.Money;
import com.commerce.shipping.service.common.valueobject.NotificationType;
import com.commerce.shipping.service.common.valueobject.Quantity;
import com.commerce.shipping.service.shipping.model.Address;
import com.commerce.shipping.service.shipping.model.OrderItem;
import com.commerce.shipping.service.shipping.port.messaging.input.OrderNotificationMessageListener;
import com.commerce.shipping.service.shipping.usecase.OrderNotificationMessage;
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
 * @Created 09.03.2024
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
    @KafkaListener(id = "${kafka-consumer-config.order-shipping-consumer-group-id}",
            topics = "${shipping-service.notification-request-topic-name}")
    public void receive(@Payload List<String> messages,
                        @Header(KafkaHeaders.RECEIVED_KEY) List<String> keys,
                        @Header(KafkaHeaders.RECEIVED_PARTITION) List<Integer> partitions,
                        @Header(KafkaHeaders.OFFSET) List<Long> offsets) {

        logger.info("{} number of messages received with keys:{}, partitions:{} and offsets:{}",
                messages.size(), keys, partitions, offsets);

        for (String message : messages) {
            NotificationRequestKafkaModel kafkaModel = exractDataFromJson(message);
            OrderNotificationMessage orderNotificationMessage = buildUseCase(kafkaModel);
            try {
                switch (orderNotificationMessage.notificationType()) {
                    case APPROVING -> {
                        logger.info("Notification sending approving action");
                        orderNotificationMessageListener.approving(orderNotificationMessage);
                    }
                    case CANCELLING -> {
                        logger.info("Notification sending cancelling action");
                        orderNotificationMessageListener.cancelling(orderNotificationMessage);
                    }
                    case DELIVERING, SHIPPING -> logger.warn("Wrong notification type came to shipping");
                }
            } catch (Exception e) {
                logger.error(e.getMessage());
            }
        }

    }

    private NotificationRequestKafkaModel exractDataFromJson(String strAsJson) {
        try {
            return objectMapper.readValue(strAsJson, NotificationRequestKafkaModel.class);
        } catch (JsonProcessingException e) {
            throw new ShippingInfraException("Could not read NotificationRequestKafkaModel object", e);
        }
    }

    private OrderNotificationMessage buildUseCase(NotificationRequestKafkaModel kafkaModel) {
        return new OrderNotificationMessage(kafkaModel.orderId(), kafkaModel.customerId(),
                Address.builder()
                        .city(kafkaModel.addressKafkaModel().city())
                        .county(kafkaModel.addressKafkaModel().county())
                        .neighborhood(kafkaModel.addressKafkaModel().neighborhood())
                        .street(kafkaModel.addressKafkaModel().street())
                        .postalCode(kafkaModel.addressKafkaModel().postalCode())
                        .build(),
                kafkaModel.items().stream()
                        .map(orderItemPayload -> OrderItem.builder()
                                .orderId(orderItemPayload.orderId())
                                .productId(orderItemPayload.productId())
                                .quantity(new Quantity(orderItemPayload.quantity()))
                                .price(new Money(orderItemPayload.price()))
                                .totalPrice(new Money(orderItemPayload.totalPrice()))
                                .build())
                        .toList(),
                NotificationType.valueOf(kafkaModel.notificationType().name()), kafkaModel.message());
    }
}
