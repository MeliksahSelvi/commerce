package com.commerce.order.service.saga;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.LoggerContext;
import com.commerce.order.service.appender.MemoryApender;
import com.commerce.order.service.common.exception.InventoryOutboxNotFoundException;
import com.commerce.order.service.common.exception.OrderDomainException;
import com.commerce.order.service.common.exception.OrderNotFoundException;
import com.commerce.order.service.common.valueobject.InventoryStatus;
import com.commerce.order.service.common.valueobject.OrderInventoryStatus;
import com.commerce.order.service.order.handler.adapter.FakeInventoryOutboxDataAdapter;
import com.commerce.order.service.order.handler.adapter.FakeJsonAdapter;
import com.commerce.order.service.order.handler.adapter.FakePaymentOutboxDataAdapter;
import com.commerce.order.service.order.handler.adapter.FakeSagaHelper;
import com.commerce.order.service.order.usecase.InventoryResponse;
import com.commerce.order.service.saga.adapter.FakeApprovedOrderDataAdapter;
import com.commerce.order.service.saga.helper.InventoryCheckingHelper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @Author mselvi
 * @Created 21.03.2024
 */

class InventoryCheckingHelperTest {

    private static final UUID sagaId=UUID.fromString("5bf96862-0c98-41ef-a952-e03d2ded6a6a");
    private static final UUID wrongSagaId=UUID.fromString("5bf96862-0c98-41ef-a952-e03d2d");

    InventoryCheckingHelper inventoryCheckingHelper;
    MemoryApender memoryApender;

    @BeforeEach
    void setUp() {
        inventoryCheckingHelper = new InventoryCheckingHelper(new FakeInventoryOutboxDataAdapter(), new FakePaymentOutboxDataAdapter(),
                new FakeApprovedOrderDataAdapter(), new FakeSagaHelper(), new FakeJsonAdapter());

        Logger logger = (Logger) LoggerFactory.getLogger(inventoryCheckingHelper.getClass());
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
        var inventoryResponse = buildInventoryResponseWithParameters(sagaId,1L);
        var logMessage = String.format("PaymentOutbox persisted for inventory checking process by sagaId: %s", inventoryResponse.sagaId());


        //then
        assertDoesNotThrow(() -> inventoryCheckingHelper.process(inventoryResponse));
        assertTrue(memoryApender.contains(logMessage, Level.INFO));
    }

    @Test
    void should_process_fail_when_order_not_exist() {
        //given
        var inventoryResponse = buildInventoryResponseWithParameters(sagaId,2L);

        //when
        //then
        var orderNotFoundException = assertThrows(OrderNotFoundException.class, () -> inventoryCheckingHelper.process(inventoryResponse));
        assertEquals(String.format("Order could not found with id: %d", inventoryResponse.orderId()), orderNotFoundException.getMessage());
    }

    @Test
    void should_process_fail_when_order_status_wrong() {
        //given
        var inventoryResponse = buildInventoryResponseWithParameters(sagaId,3L);

        //when
        //then
        var orderDomainException = assertThrows(OrderDomainException.class, () -> inventoryCheckingHelper.process(inventoryResponse));
        assertEquals("Order is not correct state for mark operation!", orderDomainException.getMessage());
    }

    @Test
    void should_process_fail_when_saga_id_wrong() {
        //given
        var inventoryResponse = buildInventoryResponseWithParameters(wrongSagaId,1L);

        //when
        //then
        var inventoryOutboxNotFoundException =
                assertThrows(InventoryOutboxNotFoundException.class, () -> inventoryCheckingHelper.process(inventoryResponse));
        assertEquals(String.format("InventoryOutbox could not found with sagaId: %s", wrongSagaId), inventoryOutboxNotFoundException.getMessage());
    }

    @Test
    void should_rollback() {
        //given
        var inventoryResponse = buildInventoryResponseWithParameters(sagaId,1L);
        var logMessage = String.format("InventoryOutbox updated for inventory checking rollback by sagaId: %s", inventoryResponse.sagaId());


        //then
        assertDoesNotThrow(() -> inventoryCheckingHelper.rollback(inventoryResponse));
        assertTrue(memoryApender.contains(logMessage, Level.INFO));
    }

    @Test
    void should_rollback_fail_when_order_not_exist() {
        //given
        var inventoryResponse = buildInventoryResponseWithParameters(sagaId,2L);

        //when
        //then
        var orderNotFoundException = assertThrows(OrderNotFoundException.class, () -> inventoryCheckingHelper.rollback(inventoryResponse));
        assertEquals(String.format("Order could not found with id: %d", inventoryResponse.orderId()), orderNotFoundException.getMessage());
    }

    @Test
    void should_rollback_fail_when_order_status_wrong() {
        //given
        var inventoryResponse = buildInventoryResponseWithParameters(sagaId,3L);

        //when
        //then
        var orderDomainException = assertThrows(OrderDomainException.class, () -> inventoryCheckingHelper.rollback(inventoryResponse));
        assertEquals("Order is not correct state for cancel operation!", orderDomainException.getMessage());
    }

    @Test
    void should_rollback_fail_when_saga_id_wrong() {
        //given
        var inventoryResponse = buildInventoryResponseWithParameters(wrongSagaId,1L);

        //when
        //then
        var inventoryOutboxNotFoundException =
                assertThrows(InventoryOutboxNotFoundException.class, () -> inventoryCheckingHelper.rollback(inventoryResponse));
        assertEquals(String.format("InventoryOutbox could not found with sagaId: %s", wrongSagaId), inventoryOutboxNotFoundException.getMessage());
    }

    private InventoryResponse buildInventoryResponseWithParameters(UUID sagaId, Long orderId) {
        return new InventoryResponse(sagaId, orderId, 1L,
                InventoryStatus.AVAILABLE, OrderInventoryStatus.CHECKING, new ArrayList<>());
    }
}
