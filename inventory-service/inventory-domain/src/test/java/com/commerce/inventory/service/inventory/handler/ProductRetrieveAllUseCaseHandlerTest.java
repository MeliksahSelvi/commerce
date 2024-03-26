package com.commerce.inventory.service.inventory.handler;

import com.commerce.inventory.service.adapter.FakeProductDataAdapter;
import com.commerce.inventory.service.inventory.usecase.ProductRetrieveAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

/**
 * @Author mselvi
 * @Created 21.03.2024
 */

class ProductRetrieveAllUseCaseHandlerTest {

    ProductRetrieveAllUseCaseHandler productRetrieveAllUseCaseHandler;

    @BeforeEach
    void setUp() {
        productRetrieveAllUseCaseHandler = new ProductRetrieveAllUseCaseHandler(new FakeProductDataAdapter());
    }

    @Test
    void should_retrieveAll() {
        //given
        var productRetrieveAll = new ProductRetrieveAll(Optional.of(0), Optional.of(3));

        //when
        var productList = productRetrieveAllUseCaseHandler.handle(productRetrieveAll);

        //then
        assertEquals(productRetrieveAll.size().get(),productList.size());
        assertNotEquals(Collections.EMPTY_LIST.size(),productList.size());
    }

    @Test
    void should_retrieveAll_empty() {
        //given
        var productRetrieveAll = new ProductRetrieveAll(Optional.of(0), Optional.of(0));

        //when
        var productList = productRetrieveAllUseCaseHandler.handle(productRetrieveAll);

        //then
        assertEquals(productRetrieveAll.size().get(),productList.size());
        assertEquals(Collections.EMPTY_LIST.size(),productList.size());
    }
}
