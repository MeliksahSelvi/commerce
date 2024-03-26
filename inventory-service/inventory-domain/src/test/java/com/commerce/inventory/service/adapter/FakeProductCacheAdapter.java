package com.commerce.inventory.service.adapter;

import com.commerce.inventory.service.inventory.port.cache.ProductCachePort;
import com.commerce.inventory.service.inventory.usecase.CachedProduct;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @Author mselvi
 * @Created 26.03.2024
 */

public class FakeProductCacheAdapter implements ProductCachePort {

    private Map<Long, CachedProduct> cachedProductMap = new ConcurrentHashMap<>();
    private Map<Long, Integer> countOfGetProduct = new ConcurrentHashMap<>();

    public FakeProductCacheAdapter() {
        cachedProductMap.put(1L, new CachedProduct(1L, 10, 0));
        cachedProductMap.put(10L, new CachedProduct(10L, 10, 0));
        countOfGetProduct.put(10L, 0);
    }

    @Override
    public void put(Long key, CachedProduct value) {
        cachedProductMap.put(key, value);
    }

    @Override
    public Optional<CachedProduct> get(Long key) {
        Integer oldCount = countOfGetProduct.get(key);
        if (oldCount != null) {
            Integer newCount = ++oldCount;
            if (newCount == 2) {
                return Optional.empty();
            }
            countOfGetProduct.put(key, newCount);
        }
        return Optional.ofNullable(cachedProductMap.get(key));
    }

    @Override
    public void remove(Long key) {
        cachedProductMap.remove(key);
    }
}
