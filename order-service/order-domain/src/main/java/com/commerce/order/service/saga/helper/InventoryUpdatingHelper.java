package com.commerce.order.service.saga.helper;

import com.commerce.order.service.common.DomainComponent;
import com.commerce.order.service.common.exception.InventoryOutboxNotFoundException;
import com.commerce.order.service.common.exception.OrderNotFoundException;
import com.commerce.order.service.common.outbox.OutboxStatus;
import com.commerce.order.service.common.saga.SagaStatus;
import com.commerce.order.service.common.valueobject.NotificationType;
import com.commerce.order.service.common.valueobject.OrderPaymentStatus;
import com.commerce.order.service.common.valueobject.OrderStatus;
import com.commerce.order.service.order.entity.Order;
import com.commerce.order.service.order.port.jpa.OrderDataPort;
import com.commerce.order.service.order.port.json.JsonPort;
import com.commerce.order.service.order.port.messaging.output.OrderNotificationMessagePublisher;
import com.commerce.order.service.order.port.messaging.output.OrderQueryMessagePublisher;
import com.commerce.order.service.order.usecase.InventoryResponse;
import com.commerce.order.service.order.usecase.OrderNotificationMessage;
import com.commerce.order.service.order.usecase.OrderQuery;
import com.commerce.order.service.outbox.entity.InventoryOutbox;
import com.commerce.order.service.outbox.entity.PaymentOutbox;
import com.commerce.order.service.outbox.entity.PaymentOutboxPayload;
import com.commerce.order.service.outbox.port.jpa.InventoryOutboxDataPort;
import com.commerce.order.service.outbox.port.jpa.PaymentOutboxDataPort;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;

/**
 * @Author mselvi
 * @Created 19.03.2024
 */

@DomainComponent
public class InventoryUpdatingHelper {

    private static final Logger logger = LoggerFactory.getLogger(InventoryUpdatingHelper.class);

    private final OrderNotificationMessagePublisher orderNotificationMessagePublisher;
    private final OrderQueryMessagePublisher orderQueryMessagePublisher;
    private final InventoryOutboxDataPort inventoryOutboxDataPort;
    private final PaymentOutboxDataPort paymentOutboxDataPort;
    private final OrderDataPort orderDataPort;
    private final SagaHelper sagaHelper;
    private final JsonPort jsonPort;

    public InventoryUpdatingHelper(OrderNotificationMessagePublisher orderNotificationMessagePublisher, OrderQueryMessagePublisher orderQueryMessagePublisher,
                                   InventoryOutboxDataPort inventoryOutboxDataPort, PaymentOutboxDataPort paymentOutboxDataPort,
                                   OrderDataPort orderDataPort, SagaHelper sagaHelper, JsonPort jsonPort) {
        this.orderNotificationMessagePublisher = orderNotificationMessagePublisher;
        this.orderQueryMessagePublisher = orderQueryMessagePublisher;
        this.inventoryOutboxDataPort = inventoryOutboxDataPort;
        this.paymentOutboxDataPort = paymentOutboxDataPort;
        this.orderDataPort = orderDataPort;
        this.sagaHelper = sagaHelper;
        this.jsonPort = jsonPort;
    }

    //process update inventory outbox updating-> order status approved, saga status succeeded
    //rollback update inventory outbox updating-> order status cancelled, saga status cancelled
    //rollback save payment outbox -> order status cancelling, saga status cancelling

    @Transactional
    public void process(InventoryResponse useCase) {
        Long orderId = useCase.orderId();
        UUID sagaId = useCase.sagaId();

        Order order = findOrder(orderId);
        order.approve();
        logger.info("Order approved by id: {}", orderId);

        Order savedOrder = orderDataPort.save(order);
        logger.info("Order updated by id: {}", orderId);

        sentQueryOrderToQueue(savedOrder);
        logger.info("OrderQuery sent to kafka queue by id: {}", savedOrder.getId());

        OrderStatus orderStatus = savedOrder.getOrderStatus();
        SagaStatus sagaStatus = sagaHelper.orderStatusToSagaStatus(orderStatus);

        InventoryOutbox inventoryOutbox = getInventoryOutboxBySagaId(sagaId);
        updateInventoryOutboxMessage(inventoryOutbox, orderStatus, sagaStatus);
        logger.info("InventoryOutbox updated for inventory updating with sagaId: {}", sagaId);

        sendOrderApprovedNotification(savedOrder);
        logger.info("Order approving notification sent to notification service by order id: {}", orderId);
    }

    private Order findOrder(Long orderId) {
        return orderDataPort.findById(orderId)
                .orElseThrow(() -> new OrderNotFoundException(String.format("Order could not found with id: %d", orderId)));
    }

    private void sentQueryOrderToQueue(Order savedOrder) {
        CompletableFuture.runAsync(
                () -> orderQueryMessagePublisher.publish(new OrderQuery(savedOrder.getId(), savedOrder.getOrderStatus()))
        );
    }

    private InventoryOutbox getInventoryOutboxBySagaId(UUID sagaId) {
        return inventoryOutboxDataPort.findBySagaIdAndSagaStatuses(sagaId, SagaStatus.PROCESSING)
                .orElseThrow(() -> new InventoryOutboxNotFoundException(String.format("InventoryOutbox could not found with sagaId: %s", sagaId)));
    }

    private void updateInventoryOutboxMessage(InventoryOutbox inventoryOutbox, OrderStatus orderStatus, SagaStatus sagaStatus) {
        inventoryOutbox.setOrderStatus(orderStatus);
        inventoryOutbox.setSagaStatus(sagaStatus);
        inventoryOutboxDataPort.save(inventoryOutbox);
    }

    private void sendOrderApprovedNotification(Order order) {
        Runnable task = () -> {
            OrderNotificationMessage orderNotificationMessage = new OrderNotificationMessage(order, NotificationType.APPROVING,
                    "Order has successfully completed");
            orderNotificationMessagePublisher.publish(orderNotificationMessage);
        };
        CompletableFuture.runAsync(task);
    }

    @Transactional
    public void rollback(InventoryResponse useCase) {
        Long orderId = useCase.orderId();
        UUID sagaId = useCase.sagaId();

        Order order = findOrder(orderId);
        order.initCancel(useCase.failureMessages());
        logger.info("Order cancelling action has started by order id: {}", orderId);

        Order savedOrder = orderDataPort.save(order);
        logger.info("Order updated by id: {}", orderId);

        sentQueryOrderToQueue(savedOrder);
        logger.info("OrderQuery sent to kafka queue by id: {}", savedOrder.getId());

        OrderStatus orderStatus = savedOrder.getOrderStatus();
        SagaStatus sagaStatus = sagaHelper.orderStatusToSagaStatus(orderStatus);

        InventoryOutbox inventoryOutbox = getInventoryOutboxBySagaId(sagaId);
        updateInventoryOutboxMessage(inventoryOutbox, OrderStatus.CANCELLED, SagaStatus.CANCELLED);
        logger.info("InventoryOutbox updated for inventory updating with sagaId: {}", sagaId);

        PaymentOutbox paymentOutbox = buildPaymentOutbox(sagaId, order, orderStatus, sagaStatus);
        paymentOutboxDataPort.save(paymentOutbox);
        logger.info("PaymentOutbox persisted for inventory updating with sagaId: {}", sagaId);
    }

    private PaymentOutbox buildPaymentOutbox(UUID sagaId, Order order, OrderStatus orderStatus, SagaStatus sagaStatus) {
        PaymentOutboxPayload payload = new PaymentOutboxPayload(sagaId, order, OrderPaymentStatus.CANCELLED);
        return PaymentOutbox.builder()
                .sagaId(sagaId)
                .payload(jsonPort.convertDataToJson(payload))
                .orderStatus(orderStatus)
                .sagaStatus(sagaStatus)
                .outboxStatus(OutboxStatus.STARTED)
                .build();
    }
}
