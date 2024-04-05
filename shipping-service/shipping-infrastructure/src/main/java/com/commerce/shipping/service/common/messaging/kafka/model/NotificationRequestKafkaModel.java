package com.commerce.shipping.service.common.messaging.kafka.model;

import com.commerce.shipping.service.common.valueobject.NotificationType;
import com.commerce.shipping.service.shipping.usecase.OrderNotificationMessage;

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
        this(message.orderId(), message.customerId(), message.message(), new AddressKafkaModel(message.address()), message.notificationType(),
                message.items().stream().map(OrderItemKafkaModel::new).toList());
    }
}
