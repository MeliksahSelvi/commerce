package com.commerce.order.query.service.adapters.order.data.document;

import com.commerce.order.query.service.common.search.elastic.model.IndexModel;
import com.commerce.order.query.service.common.valueobject.OrderStatus;
import com.commerce.order.query.service.order.model.OrderQuery;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.data.elasticsearch.annotations.Document;

/**
 * @Author mselvi
 * @Created 27.04.2024
 */

@Document(indexName = "order-query")
public class OrderQueryIndexModel implements IndexModel {

    @JsonProperty
    private String id;

    @JsonProperty
    private OrderStatus orderStatus;

    public OrderQuery toModel(){
        return new OrderQuery(Long.valueOf(id),orderStatus);
    }
    public OrderStatus getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(OrderStatus orderStatus) {
        this.orderStatus = orderStatus;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
