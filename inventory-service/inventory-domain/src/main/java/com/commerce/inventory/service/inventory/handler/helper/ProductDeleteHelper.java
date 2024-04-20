package com.commerce.inventory.service.inventory.handler.helper;

import com.commerce.inventory.service.common.DomainComponent;
import com.commerce.inventory.service.common.exception.ProductNotFoundException;
import com.commerce.inventory.service.inventory.entity.Product;
import com.commerce.inventory.service.inventory.port.jpa.ProductDataPort;
import com.commerce.inventory.service.inventory.usecase.ProductDelete;
import com.commerce.inventory.service.inventory.usecase.ProductRetrieve;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * @Author mselvi
 * @Created 15.04.2024
 */

@DomainComponent
public class ProductDeleteHelper {

    private static final Logger logger = LoggerFactory.getLogger(ProductDeleteHelper.class);

    private final ProductDataPort productDataPort;

    public ProductDeleteHelper(ProductDataPort productDataPort) {
        this.productDataPort = productDataPort;
    }

    @Transactional
    public void delete(ProductDelete productDelete) {
        checkProductExist(productDelete);
        productDataPort.deleteById(productDelete);
        logger.info("Product deleted by id: {}", productDelete.productId());
    }

    private void checkProductExist(ProductDelete productDelete) {
        Long productId = productDelete.productId();
        Optional<Product> productOptional = productDataPort.findById(new ProductRetrieve(productId));
        if (productOptional.isEmpty()) {
            throw new ProductNotFoundException(String.format("Product could not be found by id: %d", productId));
        }
    }
}
