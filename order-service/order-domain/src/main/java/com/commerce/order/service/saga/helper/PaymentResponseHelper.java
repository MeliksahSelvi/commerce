package com.commerce.order.service.saga.helper;

import com.commerce.order.service.common.DomainComponent;
import com.commerce.order.service.common.exception.OrderNotFoundException;
import com.commerce.order.service.common.exception.PaymentOutboxNotFoundException;
import com.commerce.order.service.common.outbox.OutboxStatus;
import com.commerce.order.service.common.saga.SagaStatus;
import com.commerce.order.service.common.valueobject.OrderInventoryStatus;
import com.commerce.order.service.common.valueobject.OrderStatus;
import com.commerce.order.service.common.valueobject.PaymentStatus;
import com.commerce.order.service.order.entity.Order;
import com.commerce.order.service.order.port.jpa.OrderDataPort;
import com.commerce.order.service.order.port.json.JsonPort;
import com.commerce.order.service.order.usecase.PaymentResponse;
import com.commerce.order.service.outbox.entity.InventoryOutbox;
import com.commerce.order.service.outbox.entity.InventoryOutboxPayload;
import com.commerce.order.service.outbox.entity.PaymentOutbox;
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
public class PaymentResponseHelper {

    private static final Logger logger = LoggerFactory.getLogger(PaymentResponseHelper.class);
    private final InventoryOutboxDataPort inventoryOutboxDataPort;
    private final PaymentOutboxDataPort paymentOutboxDataPort;
    private final OrderDataPort orderDataPort;
    private final SagaHelper sagaHelper;
    private final JsonPort jsonPort;

    public PaymentResponseHelper(InventoryOutboxDataPort inventoryOutboxDataPort, PaymentOutboxDataPort paymentOutboxDataPort,
                                 OrderDataPort orderDataPort, SagaHelper sagaHelper, JsonPort jsonPort) {
        this.inventoryOutboxDataPort = inventoryOutboxDataPort;
        this.paymentOutboxDataPort = paymentOutboxDataPort;
        this.orderDataPort = orderDataPort;
        this.sagaHelper = sagaHelper;
        this.jsonPort = jsonPort;
    }

    //process update payment outbox-> order status paid, saga status processing
    //process save inventory outbox updating -> order status paid, saga status processing
    //rollback update payment outbox-> order status cancelled, saga status cancelled
    //rollback save inventory outbox checking -> order status cancelling, saga status cancelling

    @Transactional
    public void process(PaymentResponse useCase) {
        UUID sagaId = useCase.sagaId();
        Long orderId = useCase.orderId();

        Order order = findOrder(orderId);
        order.pay();
        logger.info("Order paid with order id: {}", orderId);

        Order savedOrder = orderDataPort.save(order);
        logger.info("Order updated with order id: {}", orderId);

        OrderStatus orderStatus = savedOrder.getOrderStatus();
        SagaStatus sagaStatus = sagaHelper.orderStatusToSagaStatus(orderStatus);

        PaymentOutbox paymentOutbox = getPaymentOutboxBySagaIdSagaStatuses(sagaId, SagaStatus.PAYING);
        paymentOutbox.setOrderStatus(orderStatus);
        paymentOutbox.setSagaStatus(sagaStatus);
        paymentOutboxDataPort.save(paymentOutbox);
        logger.info("PaymentOutbox updated for payment response with sagaId: {}", sagaId);

        InventoryOutbox inventoryOutbox = buildInventoryOutbox(sagaId, order, orderStatus, sagaStatus, OrderInventoryStatus.UPDATING);
        inventoryOutboxDataPort.save(inventoryOutbox);
        logger.info("InventoryOutbox persisted for payment response with sagaId: {}", sagaId);
    }

    private Order findOrder(Long orderId) {
        return orderDataPort.findById(orderId)
                .orElseThrow(() -> new OrderNotFoundException(String.format("Order could not found with id: %d", orderId)));
    }

    private PaymentOutbox getPaymentOutboxBySagaIdSagaStatuses(UUID sagaId, SagaStatus... sagaStatuses) {
        return paymentOutboxDataPort.findBySagaIdAndSagaStatuses(sagaId, sagaStatuses)
                .orElseThrow(() -> new PaymentOutboxNotFoundException(String.format("PaymentOutbox could not found with sagaId: %d", sagaId)));
    }

    private InventoryOutbox buildInventoryOutbox(UUID sagaId, Order order, OrderStatus orderStatus, SagaStatus sagaStatus, OrderInventoryStatus orderInventoryStatus) {
        InventoryOutboxPayload inventoryOutboxPayload = new InventoryOutboxPayload(order, orderInventoryStatus);
        return InventoryOutbox.builder()
                .sagaId(sagaId)
                .orderInventoryStatus(orderInventoryStatus)
                .payload(jsonPort.convertDataToJson(inventoryOutboxPayload))
                .orderStatus(orderStatus)
                .sagaStatus(sagaStatus)
                .outboxStatus(OutboxStatus.STARTED)
                .build();
    }

    @Transactional
    public void rollback(PaymentResponse useCase) {
        UUID sagaId = useCase.sagaId();
        Long orderId = useCase.orderId();

        Order order = findOrder(orderId);
        order.cancel(useCase.failureMessages());
        logger.info("Order cancelled with order id: {}", orderId);

        Order savedOrder = orderDataPort.save(order);
        logger.info("Order updated with order id: {}", orderId);

        OrderStatus orderStatus = savedOrder.getOrderStatus();
        SagaStatus sagaStatus = sagaHelper.orderStatusToSagaStatus(orderStatus);

        PaymentOutbox paymentOutbox = getPaymentOutboxBySagaIdSagaStatuses(sagaId, getCurrentSagaStatuses(useCase.paymentStatus()));
        paymentOutbox.setOrderStatus(orderStatus);
        paymentOutbox.setSagaStatus(sagaStatus);
        paymentOutboxDataPort.save(paymentOutbox);
        logger.info("PaymentOutbox updated for payment rollback with sagaId: {}", sagaId);

        InventoryOutbox inventoryOutbox = buildInventoryOutbox(sagaId, savedOrder, OrderStatus.CANCELLING, SagaStatus.CANCELLING, OrderInventoryStatus.CHECKING_ROLLBACK);
        inventoryOutboxDataPort.save(inventoryOutbox);
        logger.info("InventoryOutbox persisted for payment rollback with sagaId: {}", sagaId);
    }

    private SagaStatus[] getCurrentSagaStatuses(PaymentStatus paymentStatus) {
        return switch (paymentStatus) {
            //sadece ödeme alınmış ise
            case COMPLETED -> new SagaStatus[]{SagaStatus.PAYING};
            //ödeme alınmış ve processing işlemi başlatılmış ise
            case CANCELLED -> new SagaStatus[]{SagaStatus.PROCESSING, SagaStatus.CANCELLING};
            //ödeme alınamamış veya ödeme alınmış processing işlemi başlatılmış ise
            case FAILED -> new SagaStatus[]{SagaStatus.PAYING, SagaStatus.PROCESSING, SagaStatus.CANCELLING};
        };
    }
}
