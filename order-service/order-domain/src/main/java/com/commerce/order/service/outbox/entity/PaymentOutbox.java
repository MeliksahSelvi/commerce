package com.commerce.order.service.outbox.entity;

import com.commerce.order.service.common.model.BaseEntity;
import com.commerce.order.service.common.outbox.OutboxStatus;
import com.commerce.order.service.common.saga.SagaStatus;
import com.commerce.order.service.common.valueobject.Money;
import com.commerce.order.service.common.valueobject.OrderPaymentStatus;
import com.commerce.order.service.common.valueobject.OrderStatus;

import java.util.UUID;

/**
 * @Author mselvi
 * @Created 02.03.2024
 */

public class PaymentOutbox extends BaseEntity {

    private final UUID sagaId;
    private final String payload;
    private OrderStatus orderStatus;
    private SagaStatus sagaStatus;
    private OutboxStatus outboxStatus;

    private PaymentOutbox(Builder builder) {
        this.setId(builder.id);
        this.sagaId = builder.sagaId;
        this.payload = builder.payload;
        this.orderStatus = builder.orderStatus;
        this.sagaStatus = builder.sagaStatus;
        this.outboxStatus = builder.outboxStatus;
    }

    public static Builder builder() {
        return new Builder();
    }

    public UUID getSagaId() {
        return sagaId;
    }

    public String getPayload() {
        return payload;
    }

    public OrderStatus getOrderStatus() {
        return orderStatus;
    }

    public SagaStatus getSagaStatus() {
        return sagaStatus;
    }

    public OutboxStatus getOutboxStatus() {
        return outboxStatus;
    }

    public void setOutboxStatus(OutboxStatus outboxStatus) {
        this.outboxStatus = outboxStatus;
    }

    public void setOrderStatus(OrderStatus orderStatus) {
        this.orderStatus = orderStatus;
    }

    public void setSagaStatus(SagaStatus sagaStatus) {
        this.sagaStatus = sagaStatus;
    }

    public static class Builder {
        private Long id;
        private UUID sagaId;
        private String payload;
        private OrderStatus orderStatus;
        private SagaStatus sagaStatus;
        private OutboxStatus outboxStatus;

        private Builder() {
        }

        public Builder id(Long val) {
            id = val;
            return this;
        }

        public Builder sagaId(UUID val) {
            sagaId = val;
            return this;
        }

        public Builder payload(String val) {
            payload = val;
            return this;
        }

        public Builder orderStatus(OrderStatus val) {
            orderStatus = val;
            return this;
        }

        public Builder sagaStatus(SagaStatus val) {
            sagaStatus = val;
            return this;
        }

        public Builder outboxStatus(OutboxStatus val) {
            outboxStatus = val;
            return this;
        }

        public PaymentOutbox build() {
            return new PaymentOutbox(this);
        }

    }
}
