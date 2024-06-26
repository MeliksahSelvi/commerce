package com.commerce.order.service.order.handler.helper;

import com.commerce.order.service.common.DomainComponent;
import com.commerce.order.service.common.exception.OrderNotFoundException;
import com.commerce.order.service.common.outbox.OutboxStatus;
import com.commerce.order.service.common.saga.SagaStatus;
import com.commerce.order.service.common.valueobject.OrderInventoryStatus;
import com.commerce.order.service.common.valueobject.OrderPaymentStatus;
import com.commerce.order.service.common.valueobject.OrderStatus;
import com.commerce.order.service.order.model.Order;
import com.commerce.order.service.order.port.jpa.OrderDataPort;
import com.commerce.order.service.order.port.json.JsonPort;
import com.commerce.order.service.order.port.messaging.output.OrderQueryMessagePublisher;
import com.commerce.order.service.order.usecase.CancelOrder;
import com.commerce.order.service.order.usecase.OrderQuery;
import com.commerce.order.service.outbox.model.InventoryOutbox;
import com.commerce.order.service.outbox.model.InventoryOutboxPayload;
import com.commerce.order.service.outbox.model.PaymentOutbox;
import com.commerce.order.service.outbox.model.PaymentOutboxPayload;
import com.commerce.order.service.outbox.port.jpa.InventoryOutboxDataPort;
import com.commerce.order.service.outbox.port.jpa.PaymentOutboxDataPort;
import com.commerce.order.service.saga.helper.SagaHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

/**
 * @Author mselvi
 * @Created 19.03.2024
 */

@DomainComponent
public class CancelOrderHelper {

    private static final Logger logger = LoggerFactory.getLogger(CancelOrderHelper.class);
    private final OrderQueryMessagePublisher orderQueryMessagePublisher;
    private final InventoryOutboxDataPort inventoryOutboxDataPort;
    private final PaymentOutboxDataPort paymentOutboxDataPort;
    private final OrderDataPort orderDataPort;
    private final SagaHelper sagaHelper;
    private final JsonPort jsonPort;

    public CancelOrderHelper(OrderQueryMessagePublisher orderQueryMessagePublisher, InventoryOutboxDataPort inventoryOutboxDataPort,
                             PaymentOutboxDataPort paymentOutboxDataPort, OrderDataPort orderDataPort,
                             SagaHelper sagaHelper, JsonPort jsonPort) {
        this.orderQueryMessagePublisher = orderQueryMessagePublisher;
        this.inventoryOutboxDataPort = inventoryOutboxDataPort;
        this.paymentOutboxDataPort = paymentOutboxDataPort;
        this.orderDataPort = orderDataPort;
        this.sagaHelper = sagaHelper;
        this.jsonPort = jsonPort;
    }

    //process save payment outbox-> order status cancelling, saga status cancelling
    //process save inventory outbox processing -> order status cancelling, saga status cancelling

    @Transactional
    public void cancelOrder(CancelOrder useCase) {
        Long orderId = useCase.orderId();

        Order order = findOrder(orderId);
        order.initCancel(Collections.emptyList());
        logger.info("Order cancelling action started by order id: {}", orderId);

        Order savedOrder = orderDataPort.save(order);
        logger.info("Order is saved with id:{} ", orderId);

        sentQueryOrderToQueue(savedOrder);
        logger.info("OrderQuery sent to kafka queue by id: {}", savedOrder.getId());

        UUID sagaId = UUID.randomUUID();
        OrderStatus orderStatus = savedOrder.getOrderStatus();
        SagaStatus sagaStatus = sagaHelper.orderStatusToSagaStatus(orderStatus);

        PaymentOutbox paymentOutbox = buildPaymentOutbox(savedOrder, sagaId, orderStatus, sagaStatus);
        paymentOutboxDataPort.save(paymentOutbox);
        logger.info("PaymentOutbox persisted with sagaId: {}", sagaId);

        InventoryOutbox inventoryOutbox = buildInventoryOutbox(savedOrder, sagaId, orderStatus, sagaStatus);
        inventoryOutboxDataPort.save(inventoryOutbox);
        logger.info("InventoryOutbox persisted with sagaId: {}", sagaId);
    }

    private Order findOrder(Long orderId) {
        return orderDataPort.findById(orderId).orElseThrow(() -> {
            throw new OrderNotFoundException(String.format("Could not find order with id: %d", orderId));
        });
    }

    private void sentQueryOrderToQueue(Order savedOrder) {
        CompletableFuture.runAsync(
                () -> orderQueryMessagePublisher.publish(new OrderQuery(savedOrder.getId(), savedOrder.getOrderStatus()))
        );
    }

    private PaymentOutbox buildPaymentOutbox(Order order, UUID sagaId, OrderStatus orderStatus, SagaStatus sagaStatus) {
        PaymentOutboxPayload payload = new PaymentOutboxPayload(sagaId, order, OrderPaymentStatus.CANCELLED);
        return PaymentOutbox.builder()
                .sagaId(sagaId)
                .payload(jsonPort.convertDataToJson(payload))
                .orderStatus(orderStatus)
                .sagaStatus(sagaStatus)
                .outboxStatus(OutboxStatus.STARTED)
                .build();
    }

    private InventoryOutbox buildInventoryOutbox(Order order, UUID sagaId, OrderStatus orderStatus, SagaStatus sagaStatus) {
        OrderInventoryStatus orderInventoryStatus = OrderInventoryStatus.UPDATING_ROLLBACK;
        InventoryOutboxPayload inventoryOutboxPayload = new InventoryOutboxPayload(order, orderInventoryStatus);
        return InventoryOutbox.builder()
                .sagaId(sagaId)
                .payload(jsonPort.convertDataToJson(inventoryOutboxPayload))
                .orderStatus(orderStatus)
                .orderInventoryStatus(orderInventoryStatus)
                .sagaStatus(sagaStatus)
                .outboxStatus(OutboxStatus.STARTED)
                .build();
    }
}
