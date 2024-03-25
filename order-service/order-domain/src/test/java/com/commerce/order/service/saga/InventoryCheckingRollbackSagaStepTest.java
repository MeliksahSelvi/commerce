package com.commerce.order.service.saga;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;
import com.commerce.order.service.adapter.helper.FakeInventoryCheckingRollbackHelper;
import com.commerce.order.service.common.LoggerTest;
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

class InventoryCheckingRollbackSagaStepTest extends LoggerTest<InventoryCheckingRollbackSagaStep> {

    private static final UUID sagaId = UUID.fromString("5bf96862-0c98-41ef-a952-e03d2ded6a6a");

    InventoryCheckingRollbackSagaStep inventoryCheckingRollbackSagaStep;

    public InventoryCheckingRollbackSagaStepTest() {
        super(InventoryCheckingRollbackSagaStep.class);
    }

    @BeforeEach
    void setUp() {
        inventoryCheckingRollbackSagaStep = new InventoryCheckingRollbackSagaStep(new FakeInventoryCheckingRollbackHelper());
    }

    @AfterEach
    void cleanUp() {
        destroy();
    }

    @Test
    void should_process() {
        //given
        var inventoryResponse = buildInventoryResponseWithParameters(sagaId);
        var logMessage = "Processing action for inventory checking rollback started with InventoryResponse";

        //when
        //then
        assertDoesNotThrow(() -> inventoryCheckingRollbackSagaStep.process(inventoryResponse));
        assertTrue(memoryApender.contains(logMessage, Level.INFO));
    }

    @Test
    void should_rollback() {
        //given
        var inventoryResponse = buildInventoryResponseWithParameters(sagaId);
        var logMessage = "Rollback action for inventory checking rollback started with InventoryResponse";

        //when
        //then
        assertDoesNotThrow(() -> inventoryCheckingRollbackSagaStep.rollback(inventoryResponse));
        assertTrue(memoryApender.contains(logMessage, Level.INFO));
    }

    private InventoryResponse buildInventoryResponseWithParameters(UUID sagaId) {
        return new InventoryResponse(sagaId, 1L, 1L, InventoryStatus.AVAILABLE, OrderInventoryStatus.CHECKING, new ArrayList<>());
    }
}
