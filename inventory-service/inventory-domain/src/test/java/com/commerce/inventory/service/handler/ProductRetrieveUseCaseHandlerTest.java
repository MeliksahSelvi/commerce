package com.commerce.inventory.service.handler;

import com.commerce.inventory.service.common.exception.ProductNotFoundException;
import com.commerce.inventory.service.handler.adapter.FakeProductDataPort;
import com.commerce.inventory.service.inventory.handler.ProductRetrieveUseCaseHandler;
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
    void setUp(){
        productRetrieveUseCaseHandler=new ProductRetrieveUseCaseHandler(new FakeProductDataPort());
    }

    @Test
    void should_customer_retrieve() {
        //given
        var productRetrieve = new ProductRetrieve(1L);

        //when
        var product = productRetrieveUseCaseHandler.handle(productRetrieve);

        //then
        assertEquals(productRetrieve.productId(), product.getId());
        assertNotNull(product);
    }

    @Test
    void should_customer_retrieve_empty() {
        //given
        var customerRetrieve = new ProductRetrieve(0L);

        //when
        //then
        assertThrows(ProductNotFoundException.class, () -> productRetrieveUseCaseHandler.handle(customerRetrieve));
    }
}
