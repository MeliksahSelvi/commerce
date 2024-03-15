package com.commerce.inventory.service.inventory.port.cache;

import com.commerce.inventory.service.inventory.usecase.CachedProduct;

import java.util.Optional;

/**
 * @Author mselvi
 * @Created 07.03.2024
 */

public interface ProductCachePort {

    void put(Long key, CachedProduct value);

    Optional<CachedProduct> get(Long key);

    void remove(Long key);
}
