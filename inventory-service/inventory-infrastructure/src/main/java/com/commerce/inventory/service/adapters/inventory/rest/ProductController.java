package com.commerce.inventory.service.adapters.inventory.rest;

import com.commerce.inventory.service.adapters.inventory.rest.dto.ProductResponse;
import com.commerce.inventory.service.adapters.inventory.rest.dto.ProductSaveCommand;
import com.commerce.inventory.service.common.handler.UseCaseHandler;
import com.commerce.inventory.service.inventory.entity.Product;
import com.commerce.inventory.service.inventory.usecase.ProductRetrieve;
import com.commerce.inventory.service.inventory.usecase.ProductRetrieveAll;
import com.commerce.inventory.service.inventory.usecase.ProductSave;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

/**
 * @Author mselvi
 * @Created 12.03.2024
 */

@RestController
@RequestMapping(value = "/api/v1/products")
public class ProductController {

    private final UseCaseHandler<List<Product>, ProductRetrieveAll> productRetrieveAllUseCaseHandler;
    private final UseCaseHandler<Product, ProductRetrieve> productRetrieveUseCaseHandler;
    private final UseCaseHandler<Product, ProductSave> productSaveUseCaseHandler;

    public ProductController(UseCaseHandler<List<Product>, ProductRetrieveAll> productRetrieveAllUseCaseHandler,
                             UseCaseHandler<Product, ProductRetrieve> productRetrieveUseCaseHandler,
                             UseCaseHandler<Product, ProductSave> productSaveUseCaseHandler) {
        this.productRetrieveAllUseCaseHandler = productRetrieveAllUseCaseHandler;
        this.productRetrieveUseCaseHandler = productRetrieveUseCaseHandler;
        this.productSaveUseCaseHandler = productSaveUseCaseHandler;
    }

    @GetMapping
    public ResponseEntity<List<ProductResponse>> findAll(Optional<Integer> pageOptional, Optional<Integer> sizeOptional) {
        var productList = productRetrieveAllUseCaseHandler.handle(new ProductRetrieveAll(pageOptional, sizeOptional));
        return ResponseEntity.ok(productList.stream().map(ProductResponse::new).toList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductResponse> findById(@PathVariable Long id) {
        var product = productRetrieveUseCaseHandler.handle(new ProductRetrieve(id));
        return ResponseEntity.ok(new ProductResponse(product));
    }

    @PostMapping
    public ResponseEntity<ProductResponse> save(@RequestBody @Valid ProductSaveCommand productSaveCommand) {
        var product = productSaveUseCaseHandler.handle(productSaveCommand.toModel());
        return ResponseEntity.status(HttpStatus.CREATED).body(new ProductResponse(product));
    }
}
