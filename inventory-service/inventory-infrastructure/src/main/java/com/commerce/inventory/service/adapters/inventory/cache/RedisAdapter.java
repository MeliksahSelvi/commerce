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

    private final RedisTemplate<String, String> redisTemplate;
    private final JsonPort jsonPort;

    public RedisAdapter(RedisTemplate<String, String> redisTemplate, JsonPort jsonPort) {
        this.redisTemplate = redisTemplate;
        this.jsonPort = jsonPort;
    }

    @Override
    public void put(Long productId, CachedProduct cachedProduct) {
        String cachedProductAsJson = jsonPort.convertDataToJson(cachedProduct);
        redisTemplate.opsForValue().set(productId.toString(), cachedProductAsJson);
    }

    @Override
    public Optional<CachedProduct> get(Long productId) {
        String cachedProductAsJson = redisTemplate.opsForValue().get(productId.toString());
        CachedProduct cachedProduct = jsonPort.exractDataFromJson(cachedProductAsJson, CachedProduct.class);
        return Optional.ofNullable(cachedProduct);
    }

    @Override
    public void remove(Long productId) {
        redisTemplate.delete(productId.toString());
    }
}
