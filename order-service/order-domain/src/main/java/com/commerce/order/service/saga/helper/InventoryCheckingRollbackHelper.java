package com.commerce.order.service.saga.helper;

import com.commerce.order.service.common.DomainComponent;
import com.commerce.order.service.common.exception.InventoryOutboxNotFoundException;
import com.commerce.order.service.common.exception.OrderNotFoundException;
import com.commerce.order.service.common.saga.SagaStatus;
import com.commerce.order.service.common.valueobject.OrderInventoryStatus;
import com.commerce.order.service.common.valueobject.OrderStatus;
import com.commerce.order.service.order.entity.Order;
import com.commerce.order.service.order.port.jpa.OrderDataPort;
import com.commerce.order.service.order.usecase.InventoryResponse;
import com.commerce.order.service.outbox.entity.InventoryOutbox;
import com.commerce.order.service.outbox.port.jpa.InventoryOutboxDataPort;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

/**
 * @Author mselvi
 * @Created 19.03.2024
 */

@DomainComponent
public class InventoryCheckingRollbackHelper {

    private static final Logger logger = LoggerFactory.getLogger(InventoryCheckingRollbackHelper.class);
    private final InventoryOutboxDataPort inventoryOutboxDataPort;
    private final OrderDataPort orderDataPort;
    private final SagaHelper sagaHelper;

    public InventoryCheckingRollbackHelper(InventoryOutboxDataPort inventoryOutboxDataPort, OrderDataPort orderDataPort, SagaHelper sagaHelper) {
        this.inventoryOutboxDataPort = inventoryOutboxDataPort;
        this.orderDataPort = orderDataPort;
        this.sagaHelper = sagaHelper;
    }

    @Transactional
    public void process(InventoryResponse inventoryResponse) {
        doActions(inventoryResponse);
    }

    @Transactional
    public void rollback(InventoryResponse inventoryResponse) {
        doActions(inventoryResponse);
    }

    private void doActions(InventoryResponse inventoryResponse) {
        Long orderId = inventoryResponse.orderId();
        UUID sagaId = inventoryResponse.sagaId();

        Order order = findOrder(orderId);
        order.updateFailureMessages(inventoryResponse.failureMessages());
        logger.info("Inventory checking roll back action has ended");

        Order savedOrder = orderDataPort.save(order);
        logger.info("Order is updated by id: {}", orderId);

        OrderStatus orderStatus = savedOrder.getOrderStatus();
        SagaStatus sagaStatus = sagaHelper.orderStatusToSagaStatus(orderStatus);

        InventoryOutbox inventoryOutbox = findInventoryOutboxBySagaId(sagaId);
        inventoryOutbox.setOrderStatus(orderStatus);
        inventoryOutbox.setSagaStatus(sagaStatus);
        inventoryOutboxDataPort.save(inventoryOutbox);
        logger.info("InventoryOutbox is updated for inventory checking rollback with sagaId: {}", sagaId);
    }

    private Order findOrder(Long orderId) {
        return orderDataPort.findById(orderId)
                .orElseThrow(() -> new OrderNotFoundException(String.format("Order could not found with id: %d", orderId)));
    }

    private InventoryOutbox findInventoryOutboxBySagaId(UUID sagaId) {
        return inventoryOutboxDataPort.findBySagaIdAndSagaStatusAndOrderInventoryStatus(sagaId, SagaStatus.CANCELLING, OrderInventoryStatus.CHECKING_ROLLBACK)
                .orElseThrow(() -> new InventoryOutboxNotFoundException(String.format("InventoryOutbox could not found with sagaId: %s", sagaId)));
    }
}
