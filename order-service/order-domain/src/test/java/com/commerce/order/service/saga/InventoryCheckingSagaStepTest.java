package com.commerce.order.service.saga;

import ch.qos.logback.classic.Level;
import com.commerce.order.service.adapter.helper.FakeInventoryCheckingHelper;
import com.commerce.order.service.common.LoggerTest;
import com.commerce.order.service.common.valueobject.InventoryStatus;
import com.commerce.order.service.common.valueobject.OrderInventoryStatus;
import com.commerce.order.service.order.usecase.InventoryResponse;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * @Author mselvi
 * @Created 21.03.2024
 */

class InventoryCheckingSagaStepTest extends LoggerTest<InventoryCheckingSagaStep> {

    private static final UUID sagaId=UUID.fromString("5bf96862-0c98-41ef-a952-e03d2ded6a6a");

    InventoryCheckingSagaStep inventoryCheckingSagaStep;

    public InventoryCheckingSagaStepTest() {
        super(InventoryCheckingSagaStep.class);
    }

    @BeforeEach
    void setUp() {
        inventoryCheckingSagaStep = new InventoryCheckingSagaStep(new FakeInventoryCheckingHelper());

    }

    @AfterEach
    protected void cleanUp() {
        cleanUpActions();
    }

    @Test
    void should_process() {
        //given
        var inventoryResponse = buildInventoryResponseWithParameters(sagaId);
        var logMessage="Processing action for inventory checking started with InventoryResponse";

        //when
        //then
        assertDoesNotThrow(() -> inventoryCheckingSagaStep.process(inventoryResponse));
        assertTrue(memoryApender.contains(logMessage, Level.INFO));
    }

    @Test
    void should_rollback(){
        //given
        var inventoryResponse = buildInventoryResponseWithParameters(sagaId);
        var logMessage="Rollback action for inventory checking started with InventoryResponse";

        //when
        //then
        assertDoesNotThrow(() -> inventoryCheckingSagaStep.rollback(inventoryResponse));
        assertTrue(memoryApender.contains(logMessage, Level.INFO));
    }

    private InventoryResponse buildInventoryResponseWithParameters(UUID sagaId) {
        return new InventoryResponse(sagaId, 1L, 1L, InventoryStatus.AVAILABLE, OrderInventoryStatus.CHECKING, new ArrayList<>());
    }
}
