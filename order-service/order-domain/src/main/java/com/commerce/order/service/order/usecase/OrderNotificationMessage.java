package com.commerce.order.service.order.usecase;

import com.commerce.order.service.common.model.UseCase;
import com.commerce.order.service.common.valueobject.NotificationType;
import com.commerce.order.service.order.model.Order;

/**
 * @Author mselvi
 * @Created 08.03.2024
 */

public record OrderNotificationMessage(Order order, NotificationType notificationType,
                                       String message) implements UseCase {
}
