package com.commerce.shipping.service.shipping.adapters.messaging;

import com.commerce.shipping.service.common.DomainComponent;
import com.commerce.shipping.service.common.exception.ShippingNotFoundException;
import com.commerce.shipping.service.common.valueobject.DeliveryStatus;
import com.commerce.shipping.service.shipping.model.Shipping;
import com.commerce.shipping.service.shipping.port.jpa.ShippingDataPort;
import com.commerce.shipping.service.shipping.usecase.OrderNotificationMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * @Author mselvi
 * @Created 15.04.2024
 */

@DomainComponent
public class OrderNotificationListenerHelper {

    private static final Logger logger = LoggerFactory.getLogger(OrderNotificationListenerHelper.class);
    private final ShippingDataPort shippingDataPort;

    public OrderNotificationListenerHelper(ShippingDataPort shippingDataPort) {
        this.shippingDataPort = shippingDataPort;
    }

    @Transactional
    public void approving(OrderNotificationMessage message) {
        Long orderId = message.orderId();
        Optional<Shipping> shippingOptional = getShippingByOrderIdAndDeliveryStatus(orderId, DeliveryStatus.APPROVED);
        if (shippingOptional.isPresent()) {
            logger.info("This order shipping has already processed by orderId: {}", orderId);
            return;
        }

        Shipping shipping = buildShipping(message);
        logger.info("Shipping initiated by orderId: {}", orderId);
        shippingDataPort.save(shipping);
        logger.info("Shipping persisted for approving by orderId: {}", orderId);
    }

    private Optional<Shipping> getShippingByOrderIdAndDeliveryStatus(Long orderId, DeliveryStatus deliveryStatus) {
        return shippingDataPort.findByOrderIdAndDeliveryStatus(orderId, deliveryStatus);
    }

    private Shipping buildShipping(OrderNotificationMessage message) {
        Shipping shipping = Shipping.builder()
                .orderId(message.orderId())
                .customerId(message.customerId())
                .address(message.address())
                .items(message.items())
                .build();
        shipping.initializeShipping();
        return shipping;
    }

    @Transactional
    public void cancelling(OrderNotificationMessage message) {
        Long orderId = message.orderId();

        Shipping shipping = findShippingByOrderId(orderId);
        shipping.cancel();
        logger.info("Shipping cancelled for orderId: {}", orderId);
        shippingDataPort.save(shipping);
        logger.info("Shipping updated for cancelling by orderId: {}", orderId);
    }

    private Shipping findShippingByOrderId(Long orderId) {
        return shippingDataPort.findByOrderId(orderId)
                .orElseThrow(() -> new ShippingNotFoundException(String.format("Shipping could not find by order id: %d", orderId)));
    }
}
