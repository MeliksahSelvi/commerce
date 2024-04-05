package com.commerce.order.service.adapters.order.messaging.publisher;

import com.commerce.order.service.common.messaging.kafka.model.NotificationRequestKafkaModel;
import com.commerce.order.service.common.messaging.kafka.producer.KafkaProducerWithoutCallback;
import com.commerce.order.service.order.port.json.JsonPort;
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
    private final KafkaProducerWithoutCallback kafkaProducer;
    private final JsonPort jsonPort;

    @Value("${order-service.notification-request-topic-name}")
    private String notificationTopicName;


    public OrderNotificationKafkaPublisher(KafkaProducerWithoutCallback kafkaProducer, JsonPort jsonPort) {
        this.kafkaProducer = kafkaProducer;
        this.jsonPort = jsonPort;
    }

    @Override
    public void publish(OrderNotificationMessage orderNotificationMessage) {
        Long orderId = orderNotificationMessage.order().getId();
        logger.info("Received OrderNotificationMessage for order id: {} and customer id: {}", orderId, orderNotificationMessage.order().getCustomerId());

        try {
            NotificationRequestKafkaModel kafkaModel = new NotificationRequestKafkaModel(orderNotificationMessage);
            kafkaProducer.send(notificationTopicName, orderId.toString(), jsonPort.convertDataToJson(kafkaModel));

            logger.info("NotificationRequestKafkaModel sent to Kafka for order id: {}", orderId);
        } catch (Exception e) {
            logger.error("Error while sending NotificationRequestKafkaModel to kafka with order id: {}  error: {}", orderId, e.getMessage());
        }
    }

}
