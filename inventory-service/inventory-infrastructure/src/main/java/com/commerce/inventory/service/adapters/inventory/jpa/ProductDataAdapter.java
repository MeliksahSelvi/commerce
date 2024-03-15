package com.commerce.inventory.service.adapters.inventory.jpa;

import com.commerce.inventory.service.adapters.inventory.jpa.entity.ProductEntity;
import com.commerce.inventory.service.adapters.inventory.jpa.repository.ProductEntityRepository;
import com.commerce.inventory.service.inventory.entity.Product;
import com.commerce.inventory.service.inventory.port.jpa.ProductDataPort;
import com.commerce.inventory.service.inventory.usecase.ProductRetrieveAll;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * @Author mselvi
 * @Created 07.03.2024
 */

@Service
public class ProductDataAdapter implements ProductDataPort {

    private static final Integer DEFAULT_PAGE = 0;
    private static final Integer DEFAULT_SIZE = 10;
    private static final Logger logger = LoggerFactory.getLogger(ProductDataAdapter.class);
    private final ProductEntityRepository productEntityRepository;

    public ProductDataAdapter(ProductEntityRepository productEntityRepository) {
        this.productEntityRepository = productEntityRepository;
    }

    @Override
    public Optional<Product> findById(Long productId) {
        Optional<ProductEntity> productEntityOptional = productEntityRepository.findById(productId);
        return productEntityOptional.map(ProductEntity::toModel);
    }

    @Override
    public Product save(Product product) {
        var productEntity = new ProductEntity();
        productEntity.setId(product.getId());
        productEntity.setName(product.getName());
        productEntity.setPrice(product.getPrice().amount());
        productEntity.setQuantity(product.getQuantity().value());
        productEntity.setAvailability(product.getAvailability().value());
        return productEntityRepository.save(productEntity).toModel();
    }

    @Override
    public List<Product> findAll(ProductRetrieveAll productRetrieveAll) {
        PageRequest pageRequest = PageRequest.of(productRetrieveAll.page().orElse(DEFAULT_PAGE), productRetrieveAll.size().orElse(DEFAULT_SIZE));
        Page<ProductEntity> productEntities = productEntityRepository.findAll(pageRequest);
        return productEntities.stream()
                .map(ProductEntity::toModel)
                .toList();
    }
}
