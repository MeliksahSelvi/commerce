package com.commerce.order.service.saga.helper;

import com.commerce.order.service.common.DomainComponent;
import com.commerce.order.service.common.exception.InventoryOutboxNotFoundException;
import com.commerce.order.service.common.exception.OrderNotFoundException;
import com.commerce.order.service.common.saga.SagaStatus;
import com.commerce.order.service.common.valueobject.NotificationType;
import com.commerce.order.service.common.valueobject.OrderInventoryStatus;
import com.commerce.order.service.common.valueobject.OrderStatus;
import com.commerce.order.service.order.model.Order;
import com.commerce.order.service.order.port.jpa.OrderDataPort;
import com.commerce.order.service.order.port.messaging.output.OrderNotificationMessagePublisher;
import com.commerce.order.service.order.port.messaging.output.OrderQueryMessagePublisher;
import com.commerce.order.service.order.usecase.InventoryResponse;
import com.commerce.order.service.order.usecase.OrderNotificationMessage;
import com.commerce.order.service.order.usecase.OrderQuery;
import com.commerce.order.service.outbox.model.InventoryOutbox;
import com.commerce.order.service.outbox.port.jpa.InventoryOutboxDataPort;
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
public class InventoryUpdatingRollbackHelper {

    private static final Logger logger = LoggerFactory.getLogger(InventoryUpdatingRollbackHelper.class);
    private final OrderNotificationMessagePublisher orderNotificationMessagePublisher;
    private final OrderQueryMessagePublisher orderQueryMessagePublisher;
    private final InventoryOutboxDataPort inventoryOutboxDataPort;
    private final OrderDataPort orderDataPort;
    private final SagaHelper sagaHelper;

    public InventoryUpdatingRollbackHelper(OrderNotificationMessagePublisher orderNotificationMessagePublisher,
                                           OrderQueryMessagePublisher orderQueryMessagePublisher,
                                           InventoryOutboxDataPort inventoryOutboxDataPort,
                                           OrderDataPort orderDataPort, SagaHelper sagaHelper) {
        this.orderNotificationMessagePublisher = orderNotificationMessagePublisher;
        this.orderQueryMessagePublisher = orderQueryMessagePublisher;
        this.inventoryOutboxDataPort = inventoryOutboxDataPort;
        this.orderDataPort = orderDataPort;
        this.sagaHelper = sagaHelper;
    }

    @Transactional
    public void process(InventoryResponse inventoryResponse) {
        Order order = doActionsAndFindOrder(inventoryResponse);

        sendOrderCancelledNotification(order);
    }

    @Transactional
    public void rollback(InventoryResponse inventoryResponse) {
        doActionsAndFindOrder(inventoryResponse);
    }

    private Order doActionsAndFindOrder(InventoryResponse inventoryResponse) {
        Long orderId = inventoryResponse.orderId();
        UUID sagaId = inventoryResponse.sagaId();

        Order order = findOrder(orderId);
        order.cancel(inventoryResponse.failureMessages());
        logger.info("Order cancelled by id: {}", orderId);

        Order savedOrder = orderDataPort.save(order);
        logger.info("Order updated by id: {}", orderId);

        sentQueryOrderToQueue(savedOrder);
        logger.info("OrderQuery sent to kafka queue by id: {}", savedOrder.getId());

        OrderStatus orderStatus = savedOrder.getOrderStatus();
        SagaStatus sagaStatus = sagaHelper.orderStatusToSagaStatus(orderStatus);

        InventoryOutbox inventoryOutbox = getInventoryOutboxBySagaId(sagaId);
        inventoryOutbox.setOrderStatus(orderStatus);
        inventoryOutbox.setSagaStatus(sagaStatus);
        inventoryOutboxDataPort.save(inventoryOutbox);
        logger.info("InventoryOutbox updated for inventory updating rollback with sagaId: {}",sagaId);
        return savedOrder;
    }

    private Order findOrder(Long orderId) {
        return orderDataPort.findById(orderId)
                .orElseThrow(() -> new OrderNotFoundException(String.format("Order could not found with id: %d", orderId)));
    }

    private InventoryOutbox getInventoryOutboxBySagaId(UUID sagaId) {
        return inventoryOutboxDataPort.findBySagaIdAndSagaStatusAndOrderInventoryStatus(sagaId, SagaStatus.CANCELLING, OrderInventoryStatus.UPDATING_ROLLBACK)
                .orElseThrow(() -> new InventoryOutboxNotFoundException(String.format("InventoryOutbox could not found with sagaId: %s", sagaId)));
    }

    private void sentQueryOrderToQueue(Order savedOrder) {
        CompletableFuture.runAsync(
                () -> orderQueryMessagePublisher.publish(new OrderQuery(savedOrder.getId(), savedOrder.getOrderStatus()))
        );
    }

    private void sendOrderCancelledNotification(Order order) {
        Runnable task = () -> {
            OrderNotificationMessage orderNotificationMessage = new OrderNotificationMessage(order, NotificationType.CANCELLING,
                    "Order has successfully cancelled");
            orderNotificationMessagePublisher.publish(orderNotificationMessage);
        };
        CompletableFuture.runAsync(task);
    }
}
