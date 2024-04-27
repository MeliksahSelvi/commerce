package com.commerce.inventory.service.inventory.handler;

import com.commerce.inventory.service.common.DomainComponent;
import com.commerce.inventory.service.common.handler.UseCaseHandler;
import com.commerce.inventory.service.inventory.model.Product;
import com.commerce.inventory.service.inventory.port.jpa.ProductDataPort;
import com.commerce.inventory.service.inventory.usecase.ProductRetrieveAll;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * @Author mselvi
 * @Created 12.03.2024
 */

@DomainComponent
public class ProductRetrieveAllUseCaseHandler implements UseCaseHandler<List<Product>, ProductRetrieveAll> {

    private static final Logger logger = LoggerFactory.getLogger(ProductRetrieveAllUseCaseHandler.class);
    private final ProductDataPort productDataPort;

    public ProductRetrieveAllUseCaseHandler(ProductDataPort productDataPort) {
        this.productDataPort = productDataPort;
    }

    @Override
    public List<Product> handle(ProductRetrieveAll useCase) {
        List<Product> productList = productDataPort.findAll(useCase);
        logger.info("Products Retrieved for page and size values : {} , {} ", useCase.page(), useCase.size());
        return productList;
    }
}
