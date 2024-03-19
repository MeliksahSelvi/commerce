package com.commerce.order.service.order.handler.helper;

import com.commerce.order.service.common.DomainComponent;
import com.commerce.order.service.common.exception.OrderNotFoundException;
import com.commerce.order.service.common.outbox.OutboxStatus;
import com.commerce.order.service.common.saga.SagaStatus;
import com.commerce.order.service.common.valueobject.OrderInventoryStatus;
import com.commerce.order.service.common.valueobject.OrderPaymentStatus;
import com.commerce.order.service.common.valueobject.OrderStatus;
import com.commerce.order.service.order.entity.Order;
import com.commerce.order.service.order.handler.CancelOrderVoidUseCaseHandler;
import com.commerce.order.service.order.port.jpa.OrderDataPort;
import com.commerce.order.service.order.port.json.JsonPort;
import com.commerce.order.service.order.usecase.CancelOrder;
import com.commerce.order.service.outbox.entity.InventoryOutbox;
import com.commerce.order.service.outbox.entity.InventoryOutboxPayload;
import com.commerce.order.service.outbox.entity.PaymentOutbox;
import com.commerce.order.service.outbox.entity.PaymentOutboxPayload;
import com.commerce.order.service.outbox.port.jpa.InventoryOutboxDataPort;
import com.commerce.order.service.outbox.port.jpa.PaymentOutboxDataPort;
import com.commerce.order.service.saga.SagaHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.UUID;

/**
 * @Author mselvi
 * @Created 19.03.2024
 */

@DomainComponent
public class CancelOrderHelper {

    private static final Logger logger = LoggerFactory.getLogger(CancelOrderVoidUseCaseHandler.class);
    private final InventoryOutboxDataPort inventoryOutboxDataPort;
    private final PaymentOutboxDataPort paymentOutboxDataPort;
    private final OrderDataPort orderDataPort;
    private final SagaHelper sagaHelper;
    private final JsonPort jsonPort;

    public CancelOrderHelper(InventoryOutboxDataPort inventoryOutboxDataPort, PaymentOutboxDataPort paymentOutboxDataPort,
                             OrderDataPort orderDataPort, SagaHelper sagaHelper, JsonPort jsonPort) {
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

    private PaymentOutbox buildPaymentOutbox(Order order, UUID sagaId, OrderStatus orderStatus, SagaStatus sagaStatus) {
        PaymentOutboxPayload payload = new PaymentOutboxPayload(sagaId, order, OrderPaymentStatus.CANCELLED);
        PaymentOutbox paymentOutbox = PaymentOutbox.builder()
                .sagaId(sagaId)
                .payload(jsonPort.convertDataToJson(payload))
                .orderStatus(orderStatus)
                .sagaStatus(sagaStatus)
                .outboxStatus(OutboxStatus.STARTED)
                .build();
        return paymentOutbox;
    }

    private InventoryOutbox buildInventoryOutbox(Order order, UUID sagaId, OrderStatus orderStatus, SagaStatus sagaStatus) {
        OrderInventoryStatus orderInventoryStatus = OrderInventoryStatus.PROCESSING_ROLLBACK;
        InventoryOutboxPayload inventoryOutboxPayload = new InventoryOutboxPayload(order, orderInventoryStatus);
        InventoryOutbox inventoryOutbox = InventoryOutbox.builder()
                .sagaId(sagaId)
                .payload(jsonPort.convertDataToJson(inventoryOutboxPayload))
                .orderStatus(orderStatus)
                .orderInventoryStatus(orderInventoryStatus)
                .sagaStatus(sagaStatus)
                .outboxStatus(OutboxStatus.STARTED)
                .build();
        return inventoryOutbox;
    }
}
