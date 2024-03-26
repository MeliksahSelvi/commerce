package com.commerce.notification.service.adapters.notification.messaging.listener;

import com.commerce.kafka.consumer.KafkaConsumer;
import com.commerce.kafka.model.NotificationRequestAvroModel;
import com.commerce.notification.service.common.valueobject.NotificationType;
import com.commerce.notification.service.notification.port.messaging.input.OrderNotificationMessageListener;
import com.commerce.notification.service.notification.usecase.OrderNotificationMessage;
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
public class OrderNotificationKafkaListener implements KafkaConsumer<NotificationRequestAvroModel> {

    private static final Logger logger = LoggerFactory.getLogger(OrderNotificationKafkaListener.class);
    private final OrderNotificationMessageListener orderNotificationMessageListener;

    public OrderNotificationKafkaListener(OrderNotificationMessageListener orderNotificationMessageListener) {
        this.orderNotificationMessageListener = orderNotificationMessageListener;
    }

    @Override
    @KafkaListener(id = "${kafka-consumer-config.order-notification-consumer-group-id}",
            topics = "${notification-service.notification-request-topic-name}")
    public void receive(@Payload List<NotificationRequestAvroModel> messages,
                        @Header(KafkaHeaders.RECEIVED_KEY) List<String> keys,
                        @Header(KafkaHeaders.RECEIVED_PARTITION) List<Integer> partitions,
                        @Header(KafkaHeaders.OFFSET) List<Long> offsets) {

        logger.info("{} number of messages received with keys:{}, partitions:{} and offsets:{}",
                messages.size(), keys, partitions, offsets);

        for (NotificationRequestAvroModel avroModel : messages) {
            OrderNotificationMessage message = buildUseCase(avroModel);
            try {
                logger.info("Notification sending {} action for orderId: {}",message.notificationType(),message.orderId());
                orderNotificationMessageListener.processMessage(message);
            } catch (Exception e) {
                logger.error(e.getMessage());
            }
        }
    }

    private OrderNotificationMessage buildUseCase(NotificationRequestAvroModel avroModel) {
        return new OrderNotificationMessage(avroModel.getOrderId(), avroModel.getCustomerId(),
                NotificationType.valueOf(avroModel.getNotificationType().name()), avroModel.getMessage());
    }
}
