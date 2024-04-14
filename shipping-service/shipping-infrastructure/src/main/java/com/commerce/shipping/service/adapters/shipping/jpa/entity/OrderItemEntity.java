package com.commerce.shipping.service.adapters.shipping.jpa.entity;

import com.commerce.shipping.service.common.model.AbstractEntity;
import com.commerce.shipping.service.common.valueobject.Money;
import com.commerce.shipping.service.common.valueobject.Quantity;
import com.commerce.shipping.service.shipping.entity.OrderItem;
import jakarta.persistence.*;

import java.math.BigDecimal;

/**
 * @Author mselvi
 * @Created 09.03.2024
 */

@Entity
@Table(name = "ORDER_ITEM")
public class OrderItemEntity extends AbstractEntity {

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "SHIPPING_ID",nullable = false)
    private ShippingEntity shipping;

    @Column(name = "ORDER_ID",nullable = false)
    private Long orderId;

    @Column(name = "PRODUCT_ID",nullable = false)
    private Long productId;

    @Column(name = "QUANTITY",nullable = false)
    private int quantity;

    @Column(name = "PRICE",nullable = false, precision = 15, scale = 2)
    private BigDecimal price;

    @Column(name = "TOTAL_PRICE",nullable = false, precision = 15, scale = 2)
    private BigDecimal totalPrice;

    public OrderItem toModel() {
        return OrderItem.builder()
                .id(getId())
                .shippingId(shipping.getId())
                .orderId(orderId)
                .productId(productId)
                .quantity(new Quantity(quantity))
                .price(new Money(price))
                .totalPrice(new Money(totalPrice))
                .build();
    }

    public ShippingEntity getShipping() {
        return shipping;
    }

    public void setShipping(ShippingEntity shipping) {
        this.shipping = shipping;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }
}
