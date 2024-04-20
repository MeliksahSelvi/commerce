package com.commerce.inventory.service.inventory.handler;

import ch.qos.logback.classic.Level;
import com.commerce.inventory.service.adapter.FakeProductDataAdapter;
import com.commerce.inventory.service.common.LoggerTest;
import com.commerce.inventory.service.common.valueobject.Availability;
import com.commerce.inventory.service.common.valueobject.Money;
import com.commerce.inventory.service.common.valueobject.Quantity;
import com.commerce.inventory.service.inventory.handler.helper.ProductSaveHelper;
import com.commerce.inventory.service.inventory.usecase.ProductSave;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @Author mselvi
 * @Created 15.04.2024
 */

class ProductSaveHelperTest extends LoggerTest<ProductSaveHelper> {

    ProductSaveHelper helper;

    public ProductSaveHelperTest() {
        super(ProductSaveHelper.class);
    }

    @BeforeEach
    void setUp() {
        helper = new ProductSaveHelper(new FakeProductDataAdapter());
    }

    @AfterEach
    protected void cleanUp() {
        cleanUpActions();
    }

    @Test
    void should_save() {
        //given
        var productSave = new ProductSave(null, "Ali", new Money(BigDecimal.TEN), new Quantity(2), new Availability(true));
        var logMessage="Product saved for name: Ali";

        //when
        var product = helper.save(productSave);

        //then
        assertEquals(productSave.name(), product.getName());
        assertEquals(productSave.price(), product.getPrice());
        assertEquals(productSave.quantity(), product.getQuantity());
        assertEquals(productSave.availability(), product.getAvailability());
        assertNull(product.getId());
        assertTrue(memoryApender.contains(logMessage, Level.INFO));
    }
}
