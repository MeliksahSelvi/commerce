package com.commerce.inventory.service.adapters.inventory.rest.dto;

import com.commerce.inventory.service.inventory.entity.Product;

import java.math.BigDecimal;

/**
 * @Author mselvi
 * @Created 12.03.2024
 */

public record ProductResponse(Long id,String name, BigDecimal price, int quantity, boolean availability) {

    public ProductResponse(Product product) {
        this(product.getId(), product.getName(), product.getPrice().amount(), product.getQuantity().value(), product.getAvailability().value());
    }
}
