package com.commerce.order.service.outbox.scheduler;

import com.commerce.order.service.common.DomainComponent;
import com.commerce.order.service.common.outbox.OutboxScheduler;
import com.commerce.order.service.common.outbox.OutboxStatus;
import com.commerce.order.service.common.saga.SagaStatus;
import com.commerce.order.service.outbox.model.InventoryOutbox;
import com.commerce.order.service.outbox.port.jpa.InventoryOutboxDataPort;
import com.commerce.order.service.order.port.messaging.output.InventoryRequestMessagePublisher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @Author mselvi
 * @Created 04.03.2024
 */

@DomainComponent
public class InventoryOutboxScheduler implements OutboxScheduler {

    private static final Logger logger = LoggerFactory.getLogger(InventoryOutboxScheduler.class);
    private final InventoryOutboxDataPort dataPort;
    private final InventoryRequestMessagePublisher publisher;

    public InventoryOutboxScheduler(InventoryOutboxDataPort dataPort, InventoryRequestMessagePublisher publisher) {
        this.dataPort = dataPort;
        this.publisher = publisher;
    }

    @Override
    @Transactional
    @Scheduled(fixedDelayString = "${order-service.outbox-scheduler-fixed-rate}",
            initialDelayString = "${order-service.outbox-scheduler-initial-delay}")
    public void processOutboxMessage() {
        Optional<List<InventoryOutbox>> toBeProcessedOutboxMessages = dataPort
                .findByOutboxStatusAndSagaStatuses(OutboxStatus.STARTED,
                        SagaStatus.CHECKING,
                        SagaStatus.PROCESSING,
                        SagaStatus.CANCELLING);

        toBeProcessedOutboxMessages.ifPresent(inventoryOutboxMessages -> {
            logger.info("Received {} InventoryOutbox with ids: {}, sending to message bus!",
                    inventoryOutboxMessages.size(),
                    inventoryOutboxMessages.stream().map(outboxMessage -> outboxMessage.getId().toString())
                            .collect(Collectors.joining(",")));

            inventoryOutboxMessages.forEach(inventoryOutboxMessage -> publisher.publish(inventoryOutboxMessage, this::updateOutboxStatus));
            logger.info("{} InventoryOutbox sent to message bus!", inventoryOutboxMessages.size());
        });
    }

    @Transactional
    public void updateOutboxStatus(InventoryOutbox inventoryOutbox, OutboxStatus outboxStatus) {
        inventoryOutbox.setOutboxStatus(outboxStatus);
        dataPort.save(inventoryOutbox);
        logger.info("InventoryOutboxMessage is updated with outbox status: {}", outboxStatus.name());
    }
}
