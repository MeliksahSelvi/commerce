package com.commerce.inventory.service.adapters.inventory.jpa.entity;

import com.commerce.inventory.service.common.model.AbstractEntity;
import com.commerce.inventory.service.common.valueobject.Availability;
import com.commerce.inventory.service.common.valueobject.Money;
import com.commerce.inventory.service.common.valueobject.Quantity;
import com.commerce.inventory.service.inventory.entity.Product;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

import java.math.BigDecimal;

/**
 * @Author mselvi
 * @Created 07.03.2024
 */

@Entity
@Table(name = "PRODUCT")
public class ProductEntity extends AbstractEntity {

    @Column(name = "NAME",nullable = false)
    private String name;

    @Column(name = "PRICE",nullable = false, precision = 15, scale = 2)
    private BigDecimal price;

    @Column(name = "QUANTITY",nullable = false)
    private int quantity;

    @Column(name = "AVAILABILITY",nullable = false)
    private boolean availability;

    public Product toModel() {
        return Product.builder()
                .id(getId())
                .name(name)
                .price(new Money(price))
                .quantity(new Quantity(quantity))
                .availability(new Availability(availability))
                .build();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public boolean isAvailability() {
        return availability;
    }

    public void setAvailability(boolean availability) {
        this.availability = availability;
    }
}
