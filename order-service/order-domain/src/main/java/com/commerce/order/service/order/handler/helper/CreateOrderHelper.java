package com.commerce.order.service.order.handler.helper;

import com.commerce.order.service.common.DomainComponent;
import com.commerce.order.service.common.outbox.OutboxStatus;
import com.commerce.order.service.common.valueobject.OrderInventoryStatus;
import com.commerce.order.service.common.valueobject.OrderStatus;
import com.commerce.order.service.order.model.Order;
import com.commerce.order.service.order.port.jpa.OrderDataPort;
import com.commerce.order.service.order.port.json.JsonPort;
import com.commerce.order.service.order.port.messaging.output.OrderQueryMessagePublisher;
import com.commerce.order.service.order.usecase.CreateOrder;
import com.commerce.order.service.order.usecase.OrderQuery;
import com.commerce.order.service.outbox.model.InventoryOutbox;
import com.commerce.order.service.outbox.model.InventoryOutboxPayload;
import com.commerce.order.service.outbox.port.jpa.InventoryOutboxDataPort;
import com.commerce.order.service.saga.helper.SagaHelper;
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
public class CreateOrderHelper {

    private static final Logger logger = LoggerFactory.getLogger(CreateOrderHelper.class);
    private final OrderQueryMessagePublisher orderQueryMessagePublisher;
    private final InventoryOutboxDataPort inventoryOutboxDataPort;
    private final OrderDataPort orderDataPort;
    private final SagaHelper sagaHelper;
    private final JsonPort jsonPort;

    public CreateOrderHelper(OrderQueryMessagePublisher orderQueryMessagePublisher, InventoryOutboxDataPort inventoryOutboxDataPort,
                             OrderDataPort orderDataPort, SagaHelper sagaHelper, JsonPort jsonPort) {
        this.orderQueryMessagePublisher = orderQueryMessagePublisher;
        this.inventoryOutboxDataPort = inventoryOutboxDataPort;
        this.orderDataPort = orderDataPort;
        this.sagaHelper = sagaHelper;
        this.jsonPort = jsonPort;
    }

    @Transactional
    public Order createOrder(CreateOrder useCase) {
        Order order = buildOrder(useCase);
        Order savedOrder = orderDataPort.save(order);
        logger.info("Order is persisted with id: {}", savedOrder.getId());

        InventoryOutbox inventoryOutbox = buildInventoryOutbox(savedOrder);

        inventoryOutboxDataPort.save(inventoryOutbox);
        logger.info("InventoryOutbox persisted with sagaId: {}", inventoryOutbox.getSagaId());

        sentQueryOrderToQueue(savedOrder);
        logger.info("OrderQuery sent to kafka queue by id: {}", savedOrder.getId());
        return savedOrder;
    }

    private Order buildOrder(CreateOrder useCase) {
        Order order = useCase.toModel();
        order.validateOrder();
        order.initializeOrder();
        logger.info("Order initiated by id: {}", order.getId());
        return order;
    }

    private InventoryOutbox buildInventoryOutbox(Order order) {
        UUID sagaId = UUID.randomUUID();

        OrderStatus orderStatus = order.getOrderStatus();
        OrderInventoryStatus orderInventoryStatus = OrderInventoryStatus.CHECKING;

        InventoryOutboxPayload inventoryOutboxPayload = new InventoryOutboxPayload(order, orderInventoryStatus);
        return InventoryOutbox.builder()
                .sagaId(sagaId)
                .payload(jsonPort.convertDataToJson(inventoryOutboxPayload))
                .orderStatus(orderStatus)
                .orderInventoryStatus(orderInventoryStatus)
                .outboxStatus(OutboxStatus.STARTED)
                .sagaStatus(sagaHelper.orderStatusToSagaStatus(orderStatus))
                .build();
    }

    private void sentQueryOrderToQueue(Order savedOrder) {
        CompletableFuture.runAsync(
                () -> orderQueryMessagePublisher.publish(new OrderQuery(savedOrder.getId(), savedOrder.getOrderStatus()))
        );
    }
}
