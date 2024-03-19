package com.commerce.notification.service.notification.usecase;

import com.commerce.notification.service.common.model.UseCase;
import com.commerce.notification.service.common.valueobject.NotificationType;

/**
 * @Author mselvi
 * @Created 14.03.2024
 */

public record MailContent(CustomerResponse customerResponse, NotificationType notificationType) implements UseCase {
}
