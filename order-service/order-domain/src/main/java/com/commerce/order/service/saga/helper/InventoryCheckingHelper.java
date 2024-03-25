package com.commerce.order.service.saga.helper;

import com.commerce.order.service.common.DomainComponent;
import com.commerce.order.service.common.exception.InventoryOutboxNotFoundException;
import com.commerce.order.service.common.exception.OrderNotFoundException;
import com.commerce.order.service.common.outbox.OutboxStatus;
import com.commerce.order.service.common.saga.SagaStatus;
import com.commerce.order.service.common.valueobject.OrderPaymentStatus;
import com.commerce.order.service.common.valueobject.OrderStatus;
import com.commerce.order.service.order.entity.Order;
import com.commerce.order.service.order.port.jpa.OrderDataPort;
import com.commerce.order.service.order.port.json.JsonPort;
import com.commerce.order.service.order.usecase.InventoryResponse;
import com.commerce.order.service.outbox.entity.InventoryOutbox;
import com.commerce.order.service.outbox.entity.PaymentOutbox;
import com.commerce.order.service.outbox.entity.PaymentOutboxPayload;
import com.commerce.order.service.outbox.port.jpa.InventoryOutboxDataPort;
import com.commerce.order.service.outbox.port.jpa.PaymentOutboxDataPort;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

/**
 * @Author mselvi
 * @Created 19.03.2024
 */

@DomainComponent
public class InventoryCheckingHelper {

    private static final Logger logger = LoggerFactory.getLogger(InventoryCheckingHelper.class);
    private final InventoryOutboxDataPort inventoryOutboxDataPort;
    private final PaymentOutboxDataPort paymentOutboxDataPort;
    private final OrderDataPort orderDataPort;
    private final SagaHelper sagaHelper;
    private final JsonPort jsonPort;

    public InventoryCheckingHelper(InventoryOutboxDataPort inventoryOutboxDataPort, PaymentOutboxDataPort paymentOutboxDataPort,
                                   OrderDataPort orderDataPort, SagaHelper sagaHelper, JsonPort jsonPort) {
        this.inventoryOutboxDataPort = inventoryOutboxDataPort;
        this.paymentOutboxDataPort = paymentOutboxDataPort;
        this.orderDataPort = orderDataPort;
        this.sagaHelper = sagaHelper;
        this.jsonPort = jsonPort;
    }

    //process update inventory outbox updating-> order status pending, saga status paying
    //process start payment outbox -> order status pending, saga status paying
    //rollback inventory outbox updating-> order status cancelled, saga status cancelled

    @Transactional
    public void process(InventoryResponse useCase) {
        UUID sagaId = useCase.sagaId();
        Long orderId = useCase.orderId();

        Order order = findOrder(orderId);
        order.check();
        logger.info("Order checked by order id: {}", orderId);
        Order savedOrder = orderDataPort.save(order);

        OrderStatus orderStatus = savedOrder.getOrderStatus();
        SagaStatus sagaStatus = sagaHelper.orderStatusToSagaStatus(orderStatus);

        InventoryOutbox inventoryOutbox = findInventoryOutboxBySagaId(sagaId);
        updateInventoryOutbox(inventoryOutbox, orderStatus, sagaStatus);
        logger.info("InventoryOutbox updated for inventory checking process by sagaId: {}", sagaId);

        PaymentOutbox paymentOutbox = buildPaymentOutbox(sagaId, savedOrder, orderStatus, sagaStatus);
        paymentOutboxDataPort.save(paymentOutbox);
        logger.info("PaymentOutbox persisted for inventory checking process by sagaId: {}", sagaId);
    }

    private PaymentOutbox buildPaymentOutbox(UUID sagaId, Order order, OrderStatus orderStatus, SagaStatus sagaStatus) {
        PaymentOutboxPayload payload = new PaymentOutboxPayload(sagaId, order, OrderPaymentStatus.PENDING);
        return PaymentOutbox.builder()
                .sagaId(sagaId)
                .payload(jsonPort.convertDataToJson(payload))
                .orderStatus(orderStatus)
                .sagaStatus(sagaStatus)
                .outboxStatus(OutboxStatus.STARTED)
                .build();
    }

    @Transactional
    public void rollback(InventoryResponse useCase) {
        Long orderId = useCase.orderId();

        Order order = findOrder(orderId);
        order.cancel(useCase.failureMessages());
        logger.info("Order cancelled by order id: {}", orderId);
        Order savedOrder = orderDataPort.save(order);

        OrderStatus orderStatus = savedOrder.getOrderStatus();
        SagaStatus sagaStatus = sagaHelper.orderStatusToSagaStatus(orderStatus);

        InventoryOutbox inventoryOutbox = findInventoryOutboxBySagaId(useCase.sagaId());
        updateInventoryOutbox(inventoryOutbox, orderStatus, sagaStatus);
        logger.info("InventoryOutbox updated for inventory checking rollback by sagaId: {}", useCase.sagaId());
    }

    private Order findOrder(Long orderId) {
        return orderDataPort.findById(orderId)
                .orElseThrow(() -> new OrderNotFoundException(String.format("Order could not found with id: %d", orderId)));
    }

    private InventoryOutbox findInventoryOutboxBySagaId(UUID sagaId) {
        return inventoryOutboxDataPort.findBySagaIdAndSagaStatuses(sagaId, SagaStatus.CHECKING)
                .orElseThrow(() -> new InventoryOutboxNotFoundException(String.format("InventoryOutbox could not found with sagaId: %s", sagaId)));
    }

    private void updateInventoryOutbox(InventoryOutbox inventoryOutbox, OrderStatus orderStatus, SagaStatus sagaStatus) {
        inventoryOutbox.setOrderStatus(orderStatus);
        inventoryOutbox.setSagaStatus(sagaStatus);
        inventoryOutboxDataPort.save(inventoryOutbox);
    }
}
