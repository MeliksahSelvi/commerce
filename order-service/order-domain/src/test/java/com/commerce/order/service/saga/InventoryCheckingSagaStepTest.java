package com.commerce.order.service.saga;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.LoggerContext;
import com.commerce.order.service.order.adapters.messaging.adapter.FakeInventoryCheckingHelper;
import com.commerce.order.service.appender.MemoryApender;
import com.commerce.order.service.common.valueobject.InventoryStatus;
import com.commerce.order.service.common.valueobject.OrderInventoryStatus;
import com.commerce.order.service.order.usecase.InventoryResponse;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * @Author mselvi
 * @Created 21.03.2024
 */

class InventoryCheckingSagaStepTest {

    InventoryCheckingSagaStep inventoryCheckingSagaStep;
    MemoryApender memoryApender;

    @BeforeEach
    void setUp() {
        inventoryCheckingSagaStep = new InventoryCheckingSagaStep(new FakeInventoryCheckingHelper());

        Logger logger = (Logger) LoggerFactory.getLogger(inventoryCheckingSagaStep.getClass());
        memoryApender = new MemoryApender();
        memoryApender.setContext((LoggerContext) LoggerFactory.getILoggerFactory());
        logger.setLevel(Level.INFO);
        logger.addAppender(memoryApender);
        memoryApender.start();
    }

    @AfterEach
    void cleanUp() {
        memoryApender.reset();
        memoryApender.stop();
    }

    @Test
    void should_process() {
        //given
        var inventoryResponse = buildInventoryResponse();
        var logMessage="InventoryResponse processing action is started";

        //when
        //then
        assertDoesNotThrow(() -> inventoryCheckingSagaStep.process(inventoryResponse));
        assertTrue(memoryApender.contains(logMessage, Level.INFO));
    }

    @Test
    void should_update(){
        //given
        var inventoryResponse = buildInventoryResponse();
        var logMessage="InventoryResponse rollback action is started";

        //when
        //then
        assertDoesNotThrow(() -> inventoryCheckingSagaStep.rollback(inventoryResponse));
        assertTrue(memoryApender.contains(logMessage, Level.INFO));
    }

    private InventoryResponse buildInventoryResponse() {
        return new InventoryResponse(UUID.randomUUID(), 1L, 1L, InventoryStatus.AVAILABLE, OrderInventoryStatus.CHECKING, new ArrayList<>());
    }
}
