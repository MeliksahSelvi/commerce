package com.commerce.shipping.service.adapters.shipping.messaging.listener;

import com.commerce.kafka.consumer.KafkaConsumer;
import com.commerce.kafka.model.NotificationRequestAvroModel;
import com.commerce.shipping.service.common.valueobject.Money;
import com.commerce.shipping.service.common.valueobject.NotificationType;
import com.commerce.shipping.service.common.valueobject.Quantity;
import com.commerce.shipping.service.shipping.entity.Address;
import com.commerce.shipping.service.shipping.entity.OrderItem;
import com.commerce.shipping.service.shipping.port.messaging.input.OrderNotificationMessageListener;
import com.commerce.shipping.service.shipping.usecase.OrderNotificationMessage;
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
public class OrderNotificationKafkaListener implements KafkaConsumer<NotificationRequestAvroModel> {

    private static final Logger logger = LoggerFactory.getLogger(OrderNotificationKafkaListener.class);
    private final OrderNotificationMessageListener orderNotificationMessageListener;

    public OrderNotificationKafkaListener(OrderNotificationMessageListener orderNotificationMessageListener) {
        this.orderNotificationMessageListener = orderNotificationMessageListener;
    }

    @Override
    @KafkaListener(id = "${kafka-consumer-config.order-shipping-consumer-group-id}",
            topics = "${shipping-service.notification-request-topic-name}")
    public void receive(@Payload List<NotificationRequestAvroModel> messages,
                        @Header(KafkaHeaders.RECEIVED_KEY) List<String> keys,
                        @Header(KafkaHeaders.RECEIVED_PARTITION) List<Integer> partitions,
                        @Header(KafkaHeaders.OFFSET) List<Long> offsets) {

        logger.info("{} number of messages received with keys:{}, partitions:{} and offsets:{}",
                messages.size(), keys, partitions, offsets);

        for (NotificationRequestAvroModel avroModel : messages) {
            OrderNotificationMessage message = buildUseCase(avroModel);
            try {
                switch (message.notificationType()) {
                    case APPROVING -> {
                        logger.info("Notification sending approving action");
                        orderNotificationMessageListener.approving(message);
                    }
                    case CANCELLING -> {
                        logger.info("Notification sending cancelling action");
                        orderNotificationMessageListener.cancelling(message);
                    }
                    case DELIVERING, SHIPPING -> logger.warn("Wrong notification type came to shipping");
                }
            } catch (Exception e) {
                logger.error(e.getMessage());
            }
        }

    }

    private OrderNotificationMessage buildUseCase(NotificationRequestAvroModel avroModel) {
        return new OrderNotificationMessage(avroModel.getOrderId(), avroModel.getCustomerId(),
                Address.builder()
                        .city(avroModel.getAddressPayload().getCity())
                        .county(avroModel.getAddressPayload().getCounty())
                        .neighborhood(avroModel.getAddressPayload().getNeighborhood())
                        .street(avroModel.getAddressPayload().getStreet())
                        .postalCode(avroModel.getAddressPayload().getPostalCode())
                        .build(),
                avroModel.getItems().stream()
                        .map(orderItemPayload -> OrderItem.builder()
                                .orderId(orderItemPayload.getOrderId())
                                .productId(orderItemPayload.getProductId())
                                .quantity(new Quantity(orderItemPayload.getQuantity()))
                                .price(new Money(orderItemPayload.getPrice()))
                                .totalPrice(new Money(orderItemPayload.getTotalPrice()))
                                .build())
                        .toList(),
                NotificationType.valueOf(avroModel.getNotificationType().name()), avroModel.getMessage());
    }
}
