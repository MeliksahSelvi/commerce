package com.commerce.inventory.service.inventory.handler.helper;

import com.commerce.inventory.service.common.DomainComponent;
import com.commerce.inventory.service.inventory.model.Product;
import com.commerce.inventory.service.inventory.port.jpa.ProductDataPort;
import com.commerce.inventory.service.inventory.usecase.ProductSave;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;

/**
 * @Author mselvi
 * @Created 15.04.2024
 */

@DomainComponent
public class ProductSaveHelper {

    private static final Logger logger = LoggerFactory.getLogger(ProductSaveHelper.class);
    private final ProductDataPort productDataPort;

    public ProductSaveHelper(ProductDataPort productDataPort) {
        this.productDataPort = productDataPort;
    }

    @Transactional
    public Product save(ProductSave useCase) {
        Product product = Product.builder()
                .id(useCase.productId())
                .name(useCase.name())
                .quantity(useCase.quantity())
                .availability(useCase.availability())
                .price(useCase.price())
                .build();
        Product savedProduct = productDataPort.save(product);
        logger.info("Product saved for name: {}", useCase.name());
        return savedProduct;
    }
}
