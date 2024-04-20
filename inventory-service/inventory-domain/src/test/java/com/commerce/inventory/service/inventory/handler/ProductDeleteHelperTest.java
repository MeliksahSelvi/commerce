package com.commerce.inventory.service.inventory.handler;

import ch.qos.logback.classic.Level;
import com.commerce.inventory.service.adapter.FakeProductDataAdapter;
import com.commerce.inventory.service.common.LoggerTest;
import com.commerce.inventory.service.common.exception.ProductNotFoundException;
import com.commerce.inventory.service.inventory.handler.helper.ProductDeleteHelper;
import com.commerce.inventory.service.inventory.usecase.ProductDelete;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * @Author mselvi
 * @Created 15.04.2024
 */

class ProductDeleteHelperTest extends LoggerTest<ProductDeleteHelper> {

    ProductDeleteHelper helper;

    public ProductDeleteHelperTest() {
        super(ProductDeleteHelper.class);
    }

    @BeforeEach
    void setUp() {
        helper = new ProductDeleteHelper(new FakeProductDataAdapter());
    }

    @AfterEach
    protected void cleanUp() {
        cleanUpActions();
    }

    @Test
    void should_delete() {
        //given
        var productDelete = new ProductDelete(1L);
        var logMessage="Product deleted by id: 1";

        //when
        //then
        assertDoesNotThrow(() -> helper.delete(productDelete));
        assertTrue(memoryApender.contains(logMessage, Level.INFO));
    }

    @Test
    void should_delete_fail_when_customer_not_exist() {
        //given
        var productDelete = new ProductDelete(2L);
        var errorMessage="Product could not be found by id: 2";

        //when
        //then
        var exception = assertThrows(ProductNotFoundException.class, () -> helper.delete(productDelete));
        assertTrue(exception.getMessage().contains(errorMessage));
    }
}
