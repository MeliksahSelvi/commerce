package com.commerce.inventory.service.adapters.inventory.cache;

import com.commerce.inventory.service.inventory.port.cache.ProductCachePort;
import com.commerce.inventory.service.inventory.port.json.JsonPort;
import com.commerce.inventory.service.inventory.usecase.CachedProduct;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * @Author mselvi
 * @Created 08.03.2024
 */

@Service
public class RedisAdapter implements ProductCachePort {

    private static final String PRODUCT_CACHE_KEY = "CachedProduct";
    private final RedisTemplate<String, Object> redisTemplate;
    private final JsonPort jsonPort;

    public RedisAdapter(RedisTemplate<String, Object> redisTemplate, JsonPort jsonPort) {
        this.redisTemplate = redisTemplate;
        this.jsonPort = jsonPort;
    }

    @Override
    public void put(Long productId, CachedProduct cachedProduct) {
        String cachedProductAsJson = jsonPort.convertDataToJson(cachedProduct);
        redisTemplate.opsForHash().put(PRODUCT_CACHE_KEY, productId, cachedProductAsJson);
    }

    @Override
    public Optional<CachedProduct> get(Long productId) {
        boolean isProductExist = redisTemplate.opsForHash().hasKey(PRODUCT_CACHE_KEY, productId);
        if (isProductExist) {
            String cachedProductAsJson = (String) redisTemplate.opsForHash().get(PRODUCT_CACHE_KEY, productId);
            CachedProduct cachedProduct = jsonPort.exractDataFromJson(cachedProductAsJson, CachedProduct.class);
            return Optional.of(cachedProduct);
        }
        return Optional.empty();
    }

    @Override
    public void remove(Long productId) {
        redisTemplate.opsForHash().delete(PRODUCT_CACHE_KEY, productId);
    }
}
