package com.commerce.inventory.service.inventory.handler;

import ch.qos.logback.classic.Level;
import com.commerce.inventory.service.adapter.FakeProductDeleteHelper;
import com.commerce.inventory.service.common.LoggerTest;
import com.commerce.inventory.service.common.exception.ProductNotFoundException;
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

class ProductDeleteVoidUseCaseHandlerTest extends LoggerTest<ProductDeleteVoidUseCaseHandler> {

    ProductDeleteVoidUseCaseHandler handler;

    public ProductDeleteVoidUseCaseHandlerTest() {
        super(ProductDeleteVoidUseCaseHandler.class);
    }

    @BeforeEach
    void setUp() {
        handler = new ProductDeleteVoidUseCaseHandler(new FakeProductDeleteHelper());
    }

    @AfterEach
    protected void cleanUp() {
        cleanUpActions();
    }

    @Test
    void should_handle() {
        //given
        var productDelete = new ProductDelete(1L);
        var logMessage="Product delete action started by id: 1";

        //when
        //then
        assertDoesNotThrow(() -> handler.handle(productDelete));
        assertTrue(memoryApender.contains(logMessage, Level.INFO));
    }


    @Test
    void should_not_handle_when_customer_not_exist(){
        //given
        var productDelete = new ProductDelete(2L);
        var logMessage="Product delete action started by id: 2";
        var errorMessage="Product could not be found by id: 2";

        //when
        //then
        var exception = assertThrows(ProductNotFoundException.class, () -> handler.handle(productDelete));
        assertTrue(exception.getMessage().contains(errorMessage));
        assertTrue(memoryApender.contains(logMessage, Level.INFO));
    }
}
