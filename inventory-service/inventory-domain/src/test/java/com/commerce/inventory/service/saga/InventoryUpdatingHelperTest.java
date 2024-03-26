package com.commerce.inventory.service.saga;

import com.commerce.inventory.service.adapter.FakeJsonAdapter;
import com.commerce.inventory.service.adapter.FakeOrderOutboxDataAdapter;
import com.commerce.inventory.service.adapter.FakeProductCacheAdapter;
import com.commerce.inventory.service.adapter.FakeProductDataAdapter;
import com.commerce.inventory.service.common.valueobject.Money;
import com.commerce.inventory.service.common.valueobject.OrderInventoryStatus;
import com.commerce.inventory.service.common.valueobject.Quantity;
import com.commerce.inventory.service.inventory.usecase.InventoryRequest;
import com.commerce.inventory.service.inventory.usecase.OrderItem;
import com.commerce.inventory.service.saga.helper.InventoryUpdatingHelper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * @Author mselvi
 * @Created 21.03.2024
 */

class InventoryUpdatingHelperTest {

    InventoryUpdatingHelper inventoryUpdatingHelper;

    @BeforeEach
    void setUp() {
        inventoryUpdatingHelper = new InventoryUpdatingHelper(new FakeOrderOutboxDataAdapter(), new FakeProductCacheAdapter()
                , new FakeProductDataAdapter(), new FakeJsonAdapter());
    }

    @Test
    void should_process() {
        //given
        var inventoryRequest = buildInventoryRequest(1L, 1L,10,BigDecimal.ONE);

        //when
        var failureMessages = inventoryUpdatingHelper.process(inventoryRequest);

        //then
        assertTrue(failureMessages.isEmpty());
    }

    @Test
    void should_process_fail_when_product_cache_not_found() {
        //given
        var inventoryRequest = buildInventoryRequest(1L, 2L,10,BigDecimal.ONE);
        var errorMessage = String.format("Product has already sold by productId: %d", 2L);

        //when
        var failureMessages = inventoryUpdatingHelper.process(inventoryRequest);

        //then
        assertTrue(failureMessagesIncludeThatMessage(failureMessages, errorMessage));
    }

    @Test
    void should_process_fail_when_product_not_enough() {
        //given
        var inventoryRequest = buildInventoryRequest(1L, 1L,11,BigDecimal.ONE);
        var errorMessage = String.format("Product has already sold by productId: %d", 1L);

        //when
        var failureMessages = inventoryUpdatingHelper.process(inventoryRequest);

        //then
        assertTrue(failureMessagesIncludeThatMessage(failureMessages, errorMessage));
    }

    @Test
    void should_process_fail_when_product_not_found() {
        //given
        var inventoryRequest = buildInventoryRequest(1L, 2L,10,BigDecimal.ONE);
        var errorMessage = String.format("Product has already sold by productId: %d", 2L);

        //when
        var failureMessages = inventoryUpdatingHelper.process(inventoryRequest);

        //then
        assertTrue(failureMessagesIncludeThatMessage(failureMessages, errorMessage));
    }

    @Test
    void should_process_fail_when_product_has_already_sold() {
        //given
        var inventoryRequest = buildInventoryRequest(1L, 10L,10,BigDecimal.ONE);
        var errorMessage = String.format("Product has already sold by productId: %d", 10L);

        //when
        var failureMessages = inventoryUpdatingHelper.process(inventoryRequest);

        //then
        assertTrue(failureMessagesIncludeThatMessage(failureMessages, errorMessage));
    }

    @Test
    void should_rollback() {
        //given
        var inventoryRequest = buildInventoryRequest(1L, 1L,10,BigDecimal.ONE);

        //when
        var failureMessages = inventoryUpdatingHelper.rollback(inventoryRequest);

        //then
        assertTrue(failureMessages.isEmpty());
    }

    @Test
    void should_rollback_fail_when_product_not_found() {
        //given
        var inventoryRequest = buildInventoryRequest(1L, 2L,10,BigDecimal.ONE);
        var errorMessage = String.format("Product could not be found by product id: %d and order id: %d", 2L, 1L);

        //when
        var failureMessages = inventoryUpdatingHelper.rollback(inventoryRequest);

        //then
        assertTrue(failureMessagesIncludeThatMessage(failureMessages, errorMessage));
    }

    private InventoryRequest buildInventoryRequest(Long orderId, Long productId,int quantity,BigDecimal price) {
        return new InventoryRequest(UUID.randomUUID(), orderId, 1L, new Money(BigDecimal.valueOf(17)), OrderInventoryStatus.CHECKING,
                List.of(
                        new OrderItem(1L, orderId, productId, new Quantity(quantity), new Money(price), new Money(BigDecimal.TEN))
                ));
    }

    private boolean failureMessagesIncludeThatMessage(List<String> failureMessages, String thatMessage) {
        return failureMessages.stream().anyMatch(failureMessage -> failureMessage.contains(thatMessage));
    }
}
