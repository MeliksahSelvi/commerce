package com.commerce.inventory.service.inventory.handler;

import com.commerce.inventory.service.common.DomainComponent;
import com.commerce.inventory.service.common.handler.UseCaseHandler;
import com.commerce.inventory.service.inventory.entity.Product;
import com.commerce.inventory.service.inventory.port.jpa.ProductDataPort;
import com.commerce.inventory.service.inventory.usecase.ProductSave;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @Author mselvi
 * @Created 12.03.2024
 */

@DomainComponent
public class ProductSaveUseCaseHandler implements UseCaseHandler<Product, ProductSave> {

    private static final Logger logger = LoggerFactory.getLogger(ProductSaveUseCaseHandler.class);
    private final ProductDataPort productDataPort;

    public ProductSaveUseCaseHandler(ProductDataPort productDataPort) {
        this.productDataPort = productDataPort;
    }

    @Override
    public Product handle(ProductSave useCase) {
        Product product = Product.builder()
                .name(useCase.name())
                .quantity(useCase.quantity())
                .availability(useCase.availability())
                .price(useCase.price())
                .build();
        Product savedProduct = productDataPort.save(product);
        logger.info("Product saved for name {}", useCase.name());
        return savedProduct;
    }
}
