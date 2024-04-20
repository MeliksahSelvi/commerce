package com.commerce.inventory.service.inventory.handler;

import com.commerce.inventory.service.common.DomainComponent;
import com.commerce.inventory.service.common.handler.VoidUseCaseHandler;
import com.commerce.inventory.service.inventory.handler.helper.ProductDeleteHelper;
import com.commerce.inventory.service.inventory.usecase.ProductDelete;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @Author mselvi
 * @Created 15.04.2024
 */

@DomainComponent
public class ProductDeleteVoidUseCaseHandler implements VoidUseCaseHandler<ProductDelete> {

    private static final Logger logger = LoggerFactory.getLogger(ProductDeleteVoidUseCaseHandler.class);

    private final ProductDeleteHelper productDeleteHelper;

    public ProductDeleteVoidUseCaseHandler(ProductDeleteHelper productDeleteHelper) {
        this.productDeleteHelper = productDeleteHelper;
    }

    @Override
    public void handle(ProductDelete useCase) {
        logger.info("Product delete action started by id: {}", useCase.productId());
        productDeleteHelper.delete(useCase);
    }
}
