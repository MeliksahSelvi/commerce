package com.commerce.order.query.service.adapters.order.rest;

import com.commerce.order.query.service.adapters.order.rest.dto.TrackOrderResponse;
import com.commerce.order.query.service.common.handler.UseCaseHandler;
import com.commerce.order.query.service.order.model.OrderQuery;
import com.commerce.order.query.service.order.usecase.TrackOrder;
import jakarta.validation.constraints.NotNull;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author mselvi
 * @Created 27.04.2024
 */

@RestController
@RequestMapping(value = "/api/v1/orders")
public class OrderQueryController {

    private final UseCaseHandler<OrderQuery, TrackOrder> trackOrderUseCaseHandler;

    public OrderQueryController(UseCaseHandler<OrderQuery, TrackOrder> trackOrderUseCaseHandler) {
        this.trackOrderUseCaseHandler = trackOrderUseCaseHandler;
    }

    @GetMapping("/{orderId}")
    public ResponseEntity<TrackOrderResponse> trackOrder(@PathVariable @NotNull Long orderId) {
        var order = trackOrderUseCaseHandler.handle(new TrackOrder(orderId));
        return ResponseEntity.ok(new TrackOrderResponse(order));
    }
}
