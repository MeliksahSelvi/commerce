package com.commerce.order.service.common.messaging.kafka.model;

import com.commerce.order.service.common.valueobject.NotificationType;
import com.commerce.order.service.order.usecase.OrderNotificationMessage;

import java.util.List;

/**
 * @Author mselvi
 * @Created 04.04.2024
 */

public record NotificationRequestKafkaModel(Long orderId, Long customerId, String message,
                                            AddressKafkaModel addressKafkaModel,
                                            NotificationType notificationType,
                                            List<OrderItemKafkaModel> items) implements KafkaModel {

    public NotificationRequestKafkaModel(OrderNotificationMessage message) {
        this(message.order().getId(), message.order().getCustomerId(), message.message(), new AddressKafkaModel(message.order().getDeliveryAddress()),
                message.notificationType(), message.order().getItems().stream().map(OrderItemKafkaModel::new).toList());
    }
}
