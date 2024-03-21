package com.commerce.notification.service.notification.adapters.messaging.helper;

import com.commerce.notification.service.common.DomainComponent;
import com.commerce.notification.service.common.exception.NotificationDomainException;
import com.commerce.notification.service.common.valueobject.NotificationStatus;
import com.commerce.notification.service.common.valueobject.NotificationType;
import com.commerce.notification.service.notification.entity.OrderNotification;
import com.commerce.notification.service.notification.port.jpa.OrderNotificationDataPort;
import com.commerce.notification.service.notification.port.mail.MailPort;
import com.commerce.notification.service.notification.port.rest.InnerRestPort;
import com.commerce.notification.service.notification.usecase.CustomerResponse;
import com.commerce.notification.service.notification.usecase.MailContent;
import com.commerce.notification.service.notification.usecase.OrderNotificationMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.concurrent.CompletableFuture;

/**
 * @Author mselvi
 * @Created 19.03.2024
 */

@DomainComponent
public class OrderNotificationListenerHelper {

    private static final Logger logger = LoggerFactory.getLogger(OrderNotificationListenerHelper.class);
    private final OrderNotificationDataPort orderNotificationDataPort;
    private final MailPort mailPort;
    private final InnerRestPort innerRestPort;

    public OrderNotificationListenerHelper(OrderNotificationDataPort orderNotificationDataPort, MailPort mailPort, InnerRestPort innerRestPort) {
        this.orderNotificationDataPort = orderNotificationDataPort;
        this.mailPort = mailPort;
        this.innerRestPort = innerRestPort;
    }

    @Transactional
    public void processMessage(OrderNotificationMessage message) {
        Long orderId = message.orderId();
        NotificationStatus notificationStatus = notificationTypeToNotificationStatus(message.notificationType());

        Optional<OrderNotification> notificationOptional = findNotificationByOrderIdAndNotificationStatus(orderId, notificationStatus);
        if (notificationOptional.isPresent()) {
            logger.info("This {} notification has already sent by orderId: {}", notificationStatus, orderId);
            return;
        }
        OrderNotification orderNotification = buildOrderNotification(message, notificationStatus);
        orderNotificationDataPort.save(orderNotification);
        logger.info("OrderNotification persisted for orderId: {}", orderId);

        sendMail(message);
        logger.info("MailContent sent for orderId:{} and customerId: {}", orderId, message.customerId());
    }

    private NotificationStatus notificationTypeToNotificationStatus(NotificationType notificationType) {
        return switch (notificationType) {
            case APPROVING -> NotificationStatus.APPROVED;
            case SHIPPING -> NotificationStatus.SHIPPED;
            case DELIVERING -> NotificationStatus.DELIVERED;
            case CANCELLING -> NotificationStatus.CANCELLED;
        };
    }

    private Optional<OrderNotification> findNotificationByOrderIdAndNotificationStatus(Long orderId, NotificationStatus approved) {
        return orderNotificationDataPort.findByOrderIdAndNotificationStatus(orderId, approved);
    }

    private OrderNotification buildOrderNotification(OrderNotificationMessage orderNotificationMessage, NotificationStatus notificationStatus) {
        return OrderNotification.builder()
                .orderId(orderNotificationMessage.orderId())
                .customerId(orderNotificationMessage.customerId())
                .notificationStatus(notificationStatus)
                .message(orderNotificationMessage.message())
                .build();
    }

    private void sendMail(OrderNotificationMessage message) {
        Long customerId = message.customerId();
        CustomerResponse customerResponse = innerRestPort.getCustomerInfo(customerId);
        if (customerResponse == null) {
            throw new NotificationDomainException(String.format("Could not find customer with id: %d", customerId));
        }
        MailContent mailContent = new MailContent(message.orderId(), customerResponse, message.notificationType());

        Runnable task = () -> mailPort.sendMail(mailContent);
        CompletableFuture.runAsync(task);
    }
}
