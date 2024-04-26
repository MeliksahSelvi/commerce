package com.commerce.notification.service.notification.usecase;

import com.commerce.notification.service.common.model.UseCase;
import com.commerce.notification.service.common.valueobject.NotificationType;
import com.commerce.notification.service.notification.entity.Customer;

/**
 * @Author mselvi
 * @Created 14.03.2024
 */

public record MailContent(Long orderId, Customer customer,
                          NotificationType notificationType) implements UseCase {
}
