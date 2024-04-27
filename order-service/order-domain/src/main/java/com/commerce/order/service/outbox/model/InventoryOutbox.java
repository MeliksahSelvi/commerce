package com.commerce.order.service.outbox.model;

import com.commerce.order.service.common.model.BaseModel;
import com.commerce.order.service.common.outbox.OutboxStatus;
import com.commerce.order.service.common.saga.SagaStatus;
import com.commerce.order.service.common.valueobject.OrderInventoryStatus;
import com.commerce.order.service.common.valueobject.OrderStatus;

import java.util.UUID;

/**
 * @Author mselvi
 * @Created 04.03.2024
 */

public class InventoryOutbox extends BaseModel {

    private final UUID sagaId;
    private final String payload;
    private final OrderInventoryStatus orderInventoryStatus;
    private OrderStatus orderStatus;
    private SagaStatus sagaStatus;
    private OutboxStatus outboxStatus;

    private InventoryOutbox(Builder builder) {
        this.setId(builder.id);
        this.sagaId = builder.sagaId;
        this.orderStatus = builder.orderStatus;
        this.orderInventoryStatus = builder.orderInventoryStatus;
        this.payload = builder.payload;
        this.sagaStatus = builder.sagaStatus;
        this.outboxStatus = builder.outboxStatus;
    }

    public static Builder builder() {
        return new Builder();
    }

    public UUID getSagaId() {
        return sagaId;
    }

    public OrderStatus getOrderStatus() {
        return orderStatus;
    }

    public String getPayload() {
        return payload;
    }

    public SagaStatus getSagaStatus() {
        return sagaStatus;
    }

    public OrderInventoryStatus getOrderInventoryStatus() {
        return orderInventoryStatus;
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
        private OrderInventoryStatus orderInventoryStatus;
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

        public Builder orderStatus(OrderStatus val) {
            orderStatus = val;
            return this;
        }

        public Builder orderInventoryStatus(OrderInventoryStatus val) {
            orderInventoryStatus = val;
            return this;
        }

        public Builder payload(String val) {
            payload = val;
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

        public InventoryOutbox build() {
            return new InventoryOutbox(this);
        }
    }
}
