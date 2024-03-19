package com.commerce.inventory.service.inventory.usecase;

/**
 * @Author mselvi
 * @Created 11.03.2024
 */

public class CachedProduct {
    private final Long productId;
    private int baseQuantity;
    private int tempQuantity;

    public CachedProduct(Long productId, int baseQuantity, int tempQuantity) {
        this.productId = productId;
        this.baseQuantity = baseQuantity;
        this.tempQuantity = tempQuantity;
    }


    public Long getProductId() {
        return productId;
    }

    public int getBaseQuantity() {
        return baseQuantity;
    }

    public void setBaseQuantity(int baseQuantity) {
        this.baseQuantity = baseQuantity;
    }

    public void setTempQuantity(int tempQuantity) {
        this.tempQuantity = tempQuantity;
    }

    public int getTempQuantity() {
        return tempQuantity;
    }
}
