package com.commerce.inventory.service.outbox.scheduler;

import com.commerce.inventory.service.common.DomainComponent;
import com.commerce.inventory.service.common.outbox.OutboxScheduler;
import com.commerce.inventory.service.common.outbox.OutboxStatus;
import com.commerce.inventory.service.common.valueobject.OrderInventoryStatus;
import com.commerce.inventory.service.inventory.port.messaging.output.InventoryResponseMessagePublisher;
import com.commerce.inventory.service.outbox.entity.OrderOutbox;
import com.commerce.inventory.service.outbox.port.jpa.OrderOutboxDataPort;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @Author mselvi
 * @Created 07.03.2024
 */

@DomainComponent
public class OrderOutboxScheduler implements OutboxScheduler {

    private static final Logger logger = LoggerFactory.getLogger(OrderOutboxScheduler.class);
    private final OrderOutboxDataPort dataPort;
    private final InventoryResponseMessagePublisher publisher;

    public OrderOutboxScheduler(OrderOutboxDataPort dataPort, InventoryResponseMessagePublisher publisher) {
        this.dataPort = dataPort;
        this.publisher = publisher;
    }


    @Override
    @Transactional
    @Scheduled(fixedDelayString = "${inventory-service.outbox-scheduler-fixed-rate}",
            initialDelayString = "${inventory-service.outbox-scheduler-initial-delay}")
    public void processOutboxMessage() {
        Optional<List<OrderOutbox>> toBeProcessedOutboxMessages = dataPort.findByOutboxStatus(OutboxStatus.STARTED);

        toBeProcessedOutboxMessages.ifPresent(orderOutboxMessages -> {
            logger.info("Received {} OrderOutbox with ids: {}, sending to message bus!",
                    orderOutboxMessages.size(),
                    orderOutboxMessages.stream().map(outboxMessage -> outboxMessage.getId().toString())
                            .collect(Collectors.joining(",")));

            orderOutboxMessages.forEach(inventoryOutboxMessage -> publisher.publish(inventoryOutboxMessage, this::updateOutboxStatus));
            logger.info("{} OrderOutbox sent to message bus!", orderOutboxMessages.size());
        });
    }

    @Transactional
    public void updateOutboxStatus(OrderOutbox orderOutbox, OutboxStatus outboxStatus) {
        orderOutbox.setOutboxStatus(outboxStatus);
        dataPort.save(orderOutbox);
        logger.info("InventoryOutboxMessage is updated with outbox status: {}", outboxStatus.name());
    }
}
