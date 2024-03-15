package com.commerce.inventory.service.common.valueobject;

/**
 * @Author mselvi
 * @Created 07.03.2024
 */

public record Quantity(int value) {

    public boolean isGreaterThanZero() {
        return this.value > 0;
    }

    public boolean isGreaterThan(Quantity quantity) {
        return this.value > quantity.value;
    }

    public boolean isEqual(Quantity quantity) {
        return this.value == quantity.value;
    }

    public boolean isEqualOrGreaterThan(Quantity quantity) {
        return this.value >= quantity.value;
    }

    public boolean isLowerThan(Quantity quantity) {
        return this.value < quantity.value;
    }

    public Quantity add(Quantity quantity) {
        return new Quantity(this.value + quantity.value);
    }

    public Quantity substract(Quantity quantity) {
        return new Quantity(this.value - quantity.value);
    }

    public Quantity multiply(Quantity quantity) {
        return new Quantity(this.value * quantity.value);
    }
}
