package com.commerce.inventory.service.inventory.handler;

import com.commerce.inventory.service.common.DomainComponent;
import com.commerce.inventory.service.common.exception.ProductNotFoundException;
import com.commerce.inventory.service.common.handler.UseCaseHandler;
import com.commerce.inventory.service.inventory.model.Product;
import com.commerce.inventory.service.inventory.port.jpa.ProductDataPort;
import com.commerce.inventory.service.inventory.usecase.ProductRetrieve;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Optional;

/**
 * @Author mselvi
 * @Created 12.03.2024
 */

@DomainComponent
public class ProductRetrieveUseCaseHandler implements UseCaseHandler<Product, ProductRetrieve> {

    private static final Logger logger = LoggerFactory.getLogger(ProductRetrieveUseCaseHandler.class);
    private final ProductDataPort productDataPort;

    public ProductRetrieveUseCaseHandler(ProductDataPort productDataPort) {
        this.productDataPort = productDataPort;
    }

    @Override
    public Product handle(ProductRetrieve useCase) {
        Long productId = useCase.productId();
        Optional<Product> productOptional = productDataPort.findById(useCase);
        Product product = productOptional.orElseThrow(() ->
                new ProductNotFoundException(String.format("Product could not be found by id: %d", productId)));
        logger.info("Product retrieved for id : {}", productId);
        return product;
    }
}
