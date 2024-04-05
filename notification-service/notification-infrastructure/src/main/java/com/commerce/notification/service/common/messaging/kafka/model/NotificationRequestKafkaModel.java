package com.commerce.notification.service.common.messaging.kafka.model;

import com.commerce.notification.service.common.valueobject.NotificationType;

import java.util.List;

/**
 * @Author mselvi
 * @Created 04.04.2024
 */

public record NotificationRequestKafkaModel(Long orderId, Long customerId, String message,
                                            AddressKafkaModel addressKafkaModel,
                                            NotificationType notificationType,
                                            List<OrderItemKafkaModel> items) implements KafkaModel {
}
