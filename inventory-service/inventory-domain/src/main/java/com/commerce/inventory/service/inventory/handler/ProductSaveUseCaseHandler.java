package com.commerce.inventory.service.inventory.handler;

import com.commerce.inventory.service.common.DomainComponent;
import com.commerce.inventory.service.common.handler.UseCaseHandler;
import com.commerce.inventory.service.inventory.entity.Product;
import com.commerce.inventory.service.inventory.handler.helper.ProductSaveHelper;
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
    private final ProductSaveHelper productSaveHelper;

    public ProductSaveUseCaseHandler(ProductSaveHelper productSaveHelper) {
        this.productSaveHelper = productSaveHelper;
    }

    @Override
    public Product handle(ProductSave useCase) {
        logger.info("Product save action started by name: {}", useCase.name());
        return productSaveHelper.save(useCase);
    }
}
