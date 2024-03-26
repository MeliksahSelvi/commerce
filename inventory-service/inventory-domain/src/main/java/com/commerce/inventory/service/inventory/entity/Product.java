package com.commerce.inventory.service.inventory.entity;

import com.commerce.inventory.service.common.exception.InventoryDomainException;
import com.commerce.inventory.service.common.model.BaseEntity;
import com.commerce.inventory.service.common.valueobject.Availability;
import com.commerce.inventory.service.common.valueobject.Money;
import com.commerce.inventory.service.common.valueobject.Quantity;
import com.commerce.inventory.service.inventory.usecase.OrderItem;

import java.util.List;

/**
 * @Author mselvi
 * @Created 07.03.2024
 */

public class Product extends BaseEntity {
    private String name;
    private Money price;
    private Quantity quantity;
    private Availability availability;

    public void validateProduct(OrderItem orderItem, List<String> failureMessages) {
        validateProductAvailable(orderItem.productId(), failureMessages);
        validateProductPrice(orderItem, failureMessages);
        validateQuantity(orderItem, failureMessages);
    }

    private void validateProductAvailable(Long productId, List<String> failureMessages) {
        if (!availability.isAvailable()) {
            String errorMessage = String.format("Product not available situation by productId: %d", productId);
            failureMessages.add(errorMessage);
        }
    }

    private void validateProductPrice(OrderItem orderItem, List<String> failureMessages) {
        if (!price.equals(orderItem.price())) {
            String errorMessage = String.format("Product price not equal to price that you gave by productId: %d", orderItem.productId());
            failureMessages.add(errorMessage);
        }
    }

    private void validateQuantity(OrderItem orderItem, List<String> failureMessages) {
        if (orderItem.quantity().isGreaterThan(quantity)) {
            String errorMessage = String.format("Product quantity not enough for your wanting count by product Id %d", orderItem.productId());
            failureMessages.add(errorMessage);
        }
    }

    public void decreaseQuantity(Quantity difference, List<String> failureMessages) {
        Quantity newQuantity = quantity.substract(difference);
        if (!newQuantity.isEqualOrGreaterThan(new Quantity(0))) {
            String errorMessage = String.format("Quantity that you want making decrease mustn't be greater than old quantity!");
            failureMessages.add(errorMessage);
        }
        if (failureMessages.isEmpty()) {
            quantity = newQuantity;
        }
    }

    public void increaseQuantity(Quantity difference) {
        Quantity newQuantity = quantity.add(difference);
        quantity = newQuantity;
    }

    private Product(Builder builder) {
        setId(builder.id);
        name = builder.name;
        price = builder.price;
        quantity = builder.quantity;
        availability = builder.availability;
    }

    public static Builder builder() {
        return new Builder();
    }

    public String getName() {
        return name;
    }

    public Money getPrice() {
        return price;
    }

    public Quantity getQuantity() {
        return quantity;
    }

    public Availability getAvailability() {
        return availability;
    }

    public static final class Builder {
        private Long id;
        private String name;
        private Money price;
        private Quantity quantity;
        private Availability availability;

        private Builder() {
        }

        public Builder id(Long val) {
            id = val;
            return this;
        }

        public Builder name(String val) {
            name = val;
            return this;
        }

        public Builder price(Money val) {
            price = val;
            return this;
        }

        public Builder quantity(Quantity val) {
            quantity = val;
            return this;
        }

        public Builder availability(Availability val) {
            availability = val;
            return this;
        }

        public Product build() {
            return new Product(this);
        }
    }
}
