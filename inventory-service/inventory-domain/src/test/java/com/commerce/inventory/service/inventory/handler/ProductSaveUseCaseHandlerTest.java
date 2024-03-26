package com.commerce.inventory.service.inventory.handler;

import com.commerce.inventory.service.common.valueobject.Availability;
import com.commerce.inventory.service.common.valueobject.Money;
import com.commerce.inventory.service.common.valueobject.Quantity;
import com.commerce.inventory.service.adapter.FakeProductDataAdapter;
import com.commerce.inventory.service.inventory.usecase.ProductSave;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

/**
 * @Author mselvi
 * @Created 21.03.2024
 */

class ProductSaveUseCaseHandlerTest {

    ProductSaveUseCaseHandler productSaveUseCaseHandler;


    @BeforeEach
    void setUp() {
        productSaveUseCaseHandler = new ProductSaveUseCaseHandler(new FakeProductDataAdapter());
    }

    @Test
    void should_save() {
        //given
        var productSave = new ProductSave("Ali", new Money(BigDecimal.TEN), new Quantity(2), new Availability(true));

        //when
        var product = productSaveUseCaseHandler.handle(productSave);

        //then
        assertEquals(productSave.name(), product.getName());
        assertEquals(productSave.price(), product.getPrice());
        assertEquals(productSave.quantity(), product.getQuantity());
        assertEquals(productSave.availability(), product.getAvailability());
        assertNull(product.getId());
    }
}
