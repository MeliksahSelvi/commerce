package com.commerce.order.service.adapters.order.rest;

import com.commerce.order.service.adapters.order.rest.dto.OrderCreateCommand;
import com.commerce.order.service.adapters.order.rest.dto.OrderCreateResponse;
import com.commerce.order.service.common.handler.UseCaseHandler;
import com.commerce.order.service.common.handler.VoidUseCaseHandler;
import com.commerce.order.service.order.entity.Order;
import com.commerce.order.service.order.usecase.CancelOrder;
import com.commerce.order.service.order.usecase.CreateOrder;
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

    private final UseCaseHandler<Order, CreateOrder> createOrderUseCaseHandler;
    private final VoidUseCaseHandler<CancelOrder> cancelOrderVoidUseCaseHandler;

    public OrderController(UseCaseHandler<Order, CreateOrder> createOrderUseCaseHandler, VoidUseCaseHandler<CancelOrder> cancelOrderVoidUseCaseHandler) {
        this.createOrderUseCaseHandler = createOrderUseCaseHandler;
        this.cancelOrderVoidUseCaseHandler = cancelOrderVoidUseCaseHandler;
    }

    @PostMapping
    public ResponseEntity<OrderCreateResponse> createOrder(@RequestBody @Valid OrderCreateCommand orderCreateCommand) {
        var order = createOrderUseCaseHandler.handle(orderCreateCommand.toUseCase());
        return ResponseEntity.status(HttpStatus.CREATED).body(new OrderCreateResponse(order));
    }


    @PatchMapping("/{orderId}")
    public ResponseEntity<String> cancelOrder(@PathVariable @NotNull Long orderId) {
        cancelOrderVoidUseCaseHandler.handle(new CancelOrder(orderId));
        return ResponseEntity.ok("Order cancel action has been starting");
    }

}
