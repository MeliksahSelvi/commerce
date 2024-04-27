package com.commerce.inventory.service.adapter;

import com.commerce.inventory.service.common.valueobject.Availability;
import com.commerce.inventory.service.common.valueobject.Money;
import com.commerce.inventory.service.common.valueobject.Quantity;
import com.commerce.inventory.service.inventory.model.Product;
import com.commerce.inventory.service.inventory.port.jpa.ProductDataPort;
import com.commerce.inventory.service.inventory.usecase.ProductDelete;
import com.commerce.inventory.service.inventory.usecase.ProductRetrieve;
import com.commerce.inventory.service.inventory.usecase.ProductRetrieveAll;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

/**
 * @Author mselvi
 * @Created 21.03.2024
 */

public class FakeProductDataAdapter implements ProductDataPort {

    private static final Long NOT_EXIST_PRODUCT_ID = 2L;
    private static final Long NOT_AVAILABLE_PRODUCT_ID = 3L;

    @Override
    public Optional<Product> findById(ProductRetrieve productRetrieve) {
        if (productRetrieve.productId() == NOT_EXIST_PRODUCT_ID) {
            return Optional.empty();
        }

        return Optional.of(Product.builder()
                .id(productRetrieve.productId())
                .name("name1")
                .price(new Money(BigDecimal.ONE))
                .quantity(new Quantity(10))
                .availability(new Availability(NOT_AVAILABLE_PRODUCT_ID != productRetrieve.productId()))
                .build());
    }

    @Override
    public Product save(Product product) {
        return Product.builder()
                .id(product.getId())
                .name(product.getName())
                .price(product.getPrice())
                .quantity(product.getQuantity())
                .availability(product.getAvailability())
                .build();
    }

    @Override
    public List<Product> findAll(ProductRetrieveAll productRetrieveAll) {
        if (productRetrieveAll.size().get() == 0) {
            return Collections.emptyList();
        }
        return List.of(
                Product.builder()
                        .id(1L)
                        .name("name1")
                        .price(new Money(BigDecimal.TEN))
                        .quantity(new Quantity(1))
                        .availability(new Availability(true))
                        .build(),
                Product.builder()
                        .id(2L)
                        .name("name2")
                        .price(new Money(BigDecimal.ONE))
                        .quantity(new Quantity(2))
                        .availability(new Availability(true))
                        .build(),
                Product.builder()
                        .id(3L)
                        .name("name3")
                        .price(new Money(BigDecimal.valueOf(2)))
                        .quantity(new Quantity(3))
                        .availability(new Availability(false))
                        .build());
    }

    @Override
    public void deleteById(ProductDelete productDelete) {

    }
}
