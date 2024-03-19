package com.commerce.notification.service.notification.usecase;

import com.commerce.notification.service.common.model.UseCase;
import com.commerce.notification.service.common.valueobject.NotificationType;

/**
 * @Author mselvi
 * @Created 08.03.2024
 */

//todo ileride orderitem'lere ihtiyaç olursa kafkamodel'den al
public record OrderNotificationMessage(Long orderId, Long customerId, NotificationType notificationType,
                                       String message) implements UseCase {
}
