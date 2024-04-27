package com.commerce.shipping.service.shipping.handler.helper;

import com.commerce.shipping.service.common.DomainComponent;
import com.commerce.shipping.service.common.exception.ShippingNotFoundException;
import com.commerce.shipping.service.common.valueobject.DeliveryStatus;
import com.commerce.shipping.service.common.valueobject.NotificationType;
import com.commerce.shipping.service.shipping.model.Shipping;
import com.commerce.shipping.service.shipping.port.jpa.ShippingDataPort;
import com.commerce.shipping.service.shipping.port.messaging.output.OrderNotificationMessagePublisher;
import com.commerce.shipping.service.shipping.usecase.ForwardProcess;
import com.commerce.shipping.service.shipping.usecase.OrderNotificationMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;

import java.util.concurrent.CompletableFuture;

/**
 * @Author mselvi
 * @Created 15.04.2024
 */

@DomainComponent
public class ForwardProcessHelper {

    private static final Logger logger = LoggerFactory.getLogger(ForwardProcessHelper.class);
    private final OrderNotificationMessagePublisher orderNotificationMessagePublisher;
    private final ShippingDataPort shippingDataPort;

    public ForwardProcessHelper(OrderNotificationMessagePublisher orderNotificationMessagePublisher, ShippingDataPort shippingDataPort) {
        this.orderNotificationMessagePublisher = orderNotificationMessagePublisher;
        this.shippingDataPort = shippingDataPort;
    }

    @Transactional
    public Shipping forward(ForwardProcess useCase) {
        Long orderId = useCase.orderId();
        DeliveryStatus oldStatus = useCase.oldStatus();
        DeliveryStatus newStatus = useCase.newStatus();

        Shipping shipping = getShippingByOrderIdAndDeliveryStatus(orderId, oldStatus);
        shipping.forwardDeliveryStatus(newStatus);
        logger.info("Shipping delivery status:{} forwarded through newStatus: {}", oldStatus, newStatus);

        Shipping savedShipping = shippingDataPort.save(shipping);
        logger.info("Shipping updated with status:{}", newStatus);

        sendOrderNotification(savedShipping);
        logger.info("Notification has sent to notification service by delivery status: {}", newStatus);
        return savedShipping;
    }

    private Shipping getShippingByOrderIdAndDeliveryStatus(Long orderId, DeliveryStatus deliveryStatus) {
        return shippingDataPort.findByOrderIdAndDeliveryStatus(orderId, deliveryStatus)
                .orElseThrow(() -> new ShippingNotFoundException(String.format("Shipping could not found by orderId: %d", orderId)));
    }

    private void sendOrderNotification(Shipping shipping) {
        NotificationType notificationType = convertDeliveryStatusToNotificationType(shipping.getDeliveryStatus());
        Runnable task = () -> {
            OrderNotificationMessage orderNotificationMessage = new OrderNotificationMessage(shipping, notificationType,
                    "Order processing has been successfully progressed");
            orderNotificationMessagePublisher.publish(orderNotificationMessage);
        };
        CompletableFuture.runAsync(task);
    }

    private NotificationType convertDeliveryStatusToNotificationType(DeliveryStatus deliveryStatus) {
        return switch (deliveryStatus) {
            case SHIPPED -> NotificationType.SHIPPING;
            case DELIVERED -> NotificationType.DELIVERING;
            default -> NotificationType.APPROVING;
        };
    }
}
