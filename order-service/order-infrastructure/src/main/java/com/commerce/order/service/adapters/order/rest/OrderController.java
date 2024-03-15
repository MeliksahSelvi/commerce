package com.commerce.order.service.adapters.order.rest;

import com.commerce.order.service.adapters.order.rest.dto.OrderCreateCommand;
import com.commerce.order.service.adapters.order.rest.dto.OrderCreateResponse;
import com.commerce.order.service.adapters.order.rest.dto.TrackOrderResponse;
import com.commerce.order.service.common.handler.UseCaseHandler;
import com.commerce.order.service.common.handler.VoidUseCaseHandler;
import com.commerce.order.service.common.valueobject.OrderStatus;
import com.commerce.order.service.order.entity.Order;
import com.commerce.order.service.order.usecase.CancelOrder;
import com.commerce.order.service.order.usecase.CreateOrder;
import com.commerce.order.service.order.usecase.TrackOrder;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * @Author mselvi
 * @Created 01.03.2024
 */

@RestController
@RequestMapping(value = "/api/v1/orders")
public class OrderController {

    private final UseCaseHandler<OrderStatus, CreateOrder> createOrderUseCaseHandler;
    private final UseCaseHandler<Order, TrackOrder> trackOrderUseCaseHandler;
    private final VoidUseCaseHandler<CancelOrder> cancelOrderVoidUseCaseHandler;

    public OrderController(UseCaseHandler<OrderStatus, CreateOrder> createOrderUseCaseHandler, UseCaseHandler<Order, TrackOrder> trackOrderUseCaseHandler,
                           VoidUseCaseHandler<CancelOrder> cancelOrderVoidUseCaseHandler) {
        this.createOrderUseCaseHandler = createOrderUseCaseHandler;
        this.trackOrderUseCaseHandler = trackOrderUseCaseHandler;
        this.cancelOrderVoidUseCaseHandler = cancelOrderVoidUseCaseHandler;
    }


    @GetMapping("/{orderId}")
    public ResponseEntity<TrackOrderResponse> trackOrder(@PathVariable @NotNull Long orderId) {
        var order = trackOrderUseCaseHandler.handle(new TrackOrder(orderId));
        return ResponseEntity.ok(new TrackOrderResponse(order));
    }

    @PostMapping
    public ResponseEntity<OrderCreateResponse> createOrder(@RequestBody @Valid OrderCreateCommand orderCreateCommand) {
        var orderStatus = createOrderUseCaseHandler.handle(orderCreateCommand.toUseCase());
        return ResponseEntity.status(HttpStatus.CREATED).body(new OrderCreateResponse(orderStatus));
    }


    @PatchMapping("/{orderId}")
    public ResponseEntity<String> cancelOrder(@PathVariable @NotNull Long orderId){
        cancelOrderVoidUseCaseHandler.handle(new CancelOrder(orderId));
        return ResponseEntity.ok("Order cancel action has been starting");
    }

}
