package com.commerce.inventory.service.inventory.handler;

import com.commerce.inventory.service.common.exception.ProductNotFoundException;
import com.commerce.inventory.service.adapter.FakeProductDataAdapter;
import com.commerce.inventory.service.inventory.usecase.ProductRetrieve;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @Author mselvi
 * @Created 21.03.2024
 */

class ProductRetrieveUseCaseHandlerTest {

    ProductRetrieveUseCaseHandler productRetrieveUseCaseHandler;

    @BeforeEach
    void setUp() {
        productRetrieveUseCaseHandler = new ProductRetrieveUseCaseHandler(new FakeProductDataAdapter());
    }

    @Test
    void should_retrieve() {
        //given
        var productRetrieve = new ProductRetrieve(1L);


        //when
        //then
        var product=assertDoesNotThrow(() -> productRetrieveUseCaseHandler.handle(productRetrieve));
        assertEquals(productRetrieve.productId(), product.getId());
        assertNotNull(product);
    }

    @Test
    void should_retrieve_empty() {
        //given
        var customerRetrieve = new ProductRetrieve(0L);

        //when
        //then
        var exception = assertThrows(ProductNotFoundException.class, () -> productRetrieveUseCaseHandler.handle(customerRetrieve));
        assertTrue(exception.getMessage().contains("Product could not be found"));
    }
}
