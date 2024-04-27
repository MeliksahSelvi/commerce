package com.commerce.shipping.service.adapters.shipping.rest;

import com.commerce.shipping.service.adapters.shipping.rest.dto.ForwardProcessCommand;
import com.commerce.shipping.service.adapters.shipping.rest.dto.ForwardProcessResponse;
import com.commerce.shipping.service.common.handler.UseCaseHandler;
import com.commerce.shipping.service.shipping.model.Shipping;
import com.commerce.shipping.service.shipping.usecase.ForwardProcess;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author mselvi
 * @Created 09.03.2024
 */

@RestController
@RequestMapping(value = "/api/v1/shippings")
public class ShippingController {

    private final UseCaseHandler<Shipping, ForwardProcess> forwardProcessUseCaseHandler;

    public ShippingController(UseCaseHandler<Shipping, ForwardProcess> forwardProcessUseCaseHandler) {
        this.forwardProcessUseCaseHandler = forwardProcessUseCaseHandler;
    }

    @PatchMapping
    public ResponseEntity<ForwardProcessResponse> forwardProcess(@RequestBody @Valid ForwardProcessCommand forwardProcessCommand) {
        var shipping = forwardProcessUseCaseHandler.handle(forwardProcessCommand.toModel());
        return ResponseEntity.ok(new ForwardProcessResponse(shipping));
    }
}
