package com.commerce.order.service.adapters.order.messaging.publisher;

import com.commerce.kafka.model.AddressPayload;
import com.commerce.kafka.model.NotificationRequestAvroModel;
import com.commerce.kafka.model.NotificationType;
import com.commerce.kafka.model.OrderItemPayload;
import com.commerce.kafka.producer.KafkaProducerWithoutCallback;
import com.commerce.order.service.order.port.messaging.output.OrderNotificationMessagePublisher;
import com.commerce.order.service.order.usecase.OrderNotificationMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @Author mselvi
 * @Created 08.03.2024
 */

@Component
public class OrderNotificationKafkaPublisher implements OrderNotificationMessagePublisher {

    private static final Logger logger = LoggerFactory.getLogger(OrderNotificationKafkaPublisher.class);
    private final KafkaProducerWithoutCallback<String, NotificationRequestAvroModel> kafkaProducer;

    @Value("${order-service.notification-request-topic-name}")
    private String notificationTopicName;


    public OrderNotificationKafkaPublisher(KafkaProducerWithoutCallback<String, NotificationRequestAvroModel> kafkaProducer) {
        this.kafkaProducer = kafkaProducer;
    }

    @Override
    public void publish(OrderNotificationMessage orderNotificationMessage) {
        Long orderId = orderNotificationMessage.order().getId();
        logger.info("Received OrderNotificationMessage for order id: {} and customer id: {}", orderId, orderNotificationMessage.order().getCustomerId());

        try {
            NotificationRequestAvroModel avroModel = convertMessageToKafkaModel(orderNotificationMessage);
            kafkaProducer.send(notificationTopicName, orderId.toString(), avroModel);

            logger.info("NotificationRequestAvroModel sent to Kafka for order id: {}", orderId);
        } catch (Exception e) {
            logger.error("Error while sending NotificationRequestAvroModel to kafka with order id: {}  error: {}", orderId, e.getMessage());
        }
    }

    private NotificationRequestAvroModel convertMessageToKafkaModel(OrderNotificationMessage message) {
        return NotificationRequestAvroModel.newBuilder()
                .setOrderId(message.order().getId())
                .setCustomerId(message.order().getCustomerId())
                .setMessage(message.message())
                .setNotificationType(NotificationType.valueOf(message.notificationType().name()))
                .setAddressPayload(AddressPayload.newBuilder()
                        .setId(message.order().getDeliveryAddress().getId())
                        .setCity(message.order().getDeliveryAddress().getCity())
                        .setCounty(message.order().getDeliveryAddress().getCounty())
                        .setNeighborhood(message.order().getDeliveryAddress().getNeighborhood())
                        .setStreet(message.order().getDeliveryAddress().getStreet())
                        .setPostalCode(message.order().getDeliveryAddress().getPostalCode())
                        .build())
                .setItems(message.order().getItems().stream().map(orderItem -> OrderItemPayload.newBuilder()
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
