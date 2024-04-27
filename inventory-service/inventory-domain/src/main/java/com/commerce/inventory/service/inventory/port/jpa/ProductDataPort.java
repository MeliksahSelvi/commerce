package com.commerce.inventory.service.inventory.port.jpa;

import com.commerce.inventory.service.inventory.model.Product;
import com.commerce.inventory.service.inventory.usecase.ProductDelete;
import com.commerce.inventory.service.inventory.usecase.ProductRetrieve;
import com.commerce.inventory.service.inventory.usecase.ProductRetrieveAll;

import java.util.List;
import java.util.Optional;

/**
 * @Author mselvi
 * @Created 07.03.2024
 */

public interface ProductDataPort {

    Optional<Product> findById(ProductRetrieve useCase);

    Product save(Product product);

    List<Product> findAll(ProductRetrieveAll useCase);

    void deleteById(ProductDelete productDelete);
}
