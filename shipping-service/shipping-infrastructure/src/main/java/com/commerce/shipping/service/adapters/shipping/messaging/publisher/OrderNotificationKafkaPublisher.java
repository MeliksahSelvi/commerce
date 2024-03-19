package com.commerce.shipping.service.adapters.shipping.messaging.publisher;

import com.commerce.kafka.model.AddressPayload;
import com.commerce.kafka.model.NotificationRequestAvroModel;
import com.commerce.kafka.model.NotificationType;
import com.commerce.kafka.model.OrderItemPayload;
import com.commerce.kafka.producer.KafkaProducerWithoutCallback;
import com.commerce.shipping.service.shipping.port.messaging.output.OrderNotificationMessagePublisher;
import com.commerce.shipping.service.shipping.usecase.OrderNotificationMessage;
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
    private final KafkaProducerWithoutCallback<String, NotificationRequestAvroModel> kafkaProducer;

    @Value("${shipping-service.notification-request-topic-name}")
    private String notificationTopicName;

    public OrderNotificationKafkaPublisher(KafkaProducerWithoutCallback<String, NotificationRequestAvroModel> kafkaProducer) {
        this.kafkaProducer = kafkaProducer;
    }

    @Override
    public void publish(OrderNotificationMessage orderNotificationMessage) {
        Long orderId = orderNotificationMessage.orderId();
        logger.info("Received OrderNotificationMessage for order id: {} and customer id: {}", orderId);

        try {
            NotificationRequestAvroModel avroModel = buildAvroModel(orderNotificationMessage);
            kafkaProducer.send(notificationTopicName, orderId.toString(), avroModel);

            logger.info("NotificationAvroModel sent to Kafka for order id: {}", orderId);
        } catch (Exception e) {
            logger.error("Error while sending NotificationAvroModel to kafka with order id: {}  error: {}", orderId, e.getMessage());
        }
    }

    private NotificationRequestAvroModel buildAvroModel(OrderNotificationMessage message) {
        return NotificationRequestAvroModel.newBuilder()
                .setOrderId(message.orderId())
                .setCustomerId(message.customerId())
                .setAddressPayload(AddressPayload.newBuilder()
                        .setId(message.address().getId())
                        .setCity(message.address().getCity())
                        .setCounty(message.address().getCounty())
                        .setNeighborhood(message.address().getNeighborhood())
                        .setStreet(message.address().getStreet())
                        .setPostalCode(message.address().getPostalCode())
                        .build())
                .setMessage(message.message())
                .setNotificationType(NotificationType.valueOf(message.notificationType().name()))
                .setItems(message.items().stream().map(orderItem -> OrderItemPayload.newBuilder()
                        .setId(orderItem.getId())
                        .setOrderId(orderItem.getOrderId())
                        .setProductId(orderItem.getProductId())
                        .setQuantity(orderItem.getQuantity().value())
                        .setPrice(orderItem.getPrice().amount())
                        .setTotalPrice(orderItem.getTotalPrice().amount())
                        .build()).toList())
                .build();
    }
}
