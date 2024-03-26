package com.commerce.inventory.service.saga.helper;

import com.commerce.inventory.service.common.DomainComponent;
import com.commerce.inventory.service.common.outbox.OutboxStatus;
import com.commerce.inventory.service.common.valueobject.InventoryStatus;
import com.commerce.inventory.service.common.valueobject.Quantity;
import com.commerce.inventory.service.inventory.entity.Product;
import com.commerce.inventory.service.inventory.port.cache.ProductCachePort;
import com.commerce.inventory.service.inventory.port.jpa.ProductDataPort;
import com.commerce.inventory.service.inventory.port.json.JsonPort;
import com.commerce.inventory.service.inventory.usecase.CachedProduct;
import com.commerce.inventory.service.inventory.usecase.InventoryRequest;
import com.commerce.inventory.service.inventory.usecase.OrderItem;
import com.commerce.inventory.service.inventory.usecase.ProductRetrieve;
import com.commerce.inventory.service.outbox.entity.OrderOutbox;
import com.commerce.inventory.service.outbox.entity.OrderOutboxPayload;
import com.commerce.inventory.service.outbox.port.jpa.OrderOutboxDataPort;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * @Author mselvi
 * @Created 19.03.2024
 */

@DomainComponent
public class InventoryCheckingHelper {

    private static final Logger logger = LoggerFactory.getLogger(InventoryCheckingHelper.class);
    private final OrderOutboxDataPort orderOutboxDataPort;
    private final ProductCachePort productCachePort;
    private final ProductDataPort productDataPort;
    private final JsonPort jsonPort;

    public InventoryCheckingHelper(OrderOutboxDataPort orderOutboxDataPort, ProductCachePort productCachePort,
                                   ProductDataPort productDataPort, JsonPort jsonPort) {
        this.orderOutboxDataPort = orderOutboxDataPort;
        this.productCachePort = productCachePort;
        this.productDataPort = productDataPort;
        this.jsonPort = jsonPort;
    }

    @Transactional
    public List<String> process(InventoryRequest inventoryRequest) {
        List<String> failureMessages = new ArrayList<>();

        logger.info("Items checking action started");
        InventoryStatus inventoryStatus = validateItems(inventoryRequest.items(), failureMessages);
        logger.info("Items checking action finished");

        OrderOutbox orderOutbox = buildOrderOutbox(inventoryRequest, inventoryStatus, failureMessages);
        orderOutboxDataPort.save(orderOutbox);
        logger.info("OrderOutbox persisted for inventory checking action by sagaId: {}", inventoryRequest.sagaId());
        return failureMessages;
    }

    private InventoryStatus validateItems(List<OrderItem> items, List<String> failureMessages) {
        for (OrderItem item : items) {
            Long productId = item.productId();

            Optional<Product> productOptional = productDataPort.findById(new ProductRetrieve(productId));
            if (productOptional.isEmpty()) {
                String errorMessage = String.format("Product could not find by productId: %d", productId);
                failureMessages.add(errorMessage);
                logger.error(errorMessage);
                break;
            }

            Product product = productOptional.get();
            product.validateProduct(item, failureMessages);
            product.decreaseQuantity(item.quantity(), failureMessages);

            if (failureMessages.isEmpty()) {
                productCachePort.get(productId).ifPresentOrElse(
                        cachedProduct -> {
                            updateCache(item, cachedProduct);
                        },
                        () -> {
                            persistToCache(item, product);
                        }
                );
            }
        }
        return failureMessages.isEmpty() ? InventoryStatus.AVAILABLE : InventoryStatus.NON_AVAILABLE;
    }

    private void updateCache(OrderItem item, CachedProduct cachedProduct) {
        Long productId = item.productId();
        Quantity oldTempQuantity = new Quantity(cachedProduct.getTempQuantity());
        Quantity newTempQuantity = oldTempQuantity.substract(item.quantity());
        cachedProduct.setTempQuantity(newTempQuantity.value());
        productCachePort.put(productId, cachedProduct);
        logger.info("Product updated on cache by productId: {}", productId);
    }

    private void persistToCache(OrderItem item, Product product) {
        Long productId = item.productId();
        Quantity oldQuantity = product.getQuantity();
        Quantity tempQuantity = oldQuantity.substract(item.quantity());
        CachedProduct newCachedProduct = new CachedProduct(productId, oldQuantity.value(), tempQuantity.value());
        productCachePort.put(productId, newCachedProduct);
        logger.info("Product persisted on cache by productId: {}", productId);
    }

    @Transactional
    public List<String> rollback(InventoryRequest inventoryRequest) {
        List<String> failureMessages = new ArrayList<>();

        logger.info("Item quantity payback action started");
        InventoryStatus inventoryStatus = updateCacheThroughOldQuantities(inventoryRequest.items(), failureMessages);
        logger.info("Item quantity payback action finished");

        OrderOutbox orderOutbox = buildOrderOutbox(inventoryRequest, inventoryStatus, failureMessages);
        orderOutboxDataPort.save(orderOutbox);
        logger.info("OrderOutbox persisted for inventory checking rollback action by sagaId: {}", inventoryRequest.sagaId());
        return failureMessages;
    }

    private InventoryStatus updateCacheThroughOldQuantities(List<OrderItem> items, List<String> failureMessages) {
        for (OrderItem item : items) {
            Long productId = item.productId();

            Optional<CachedProduct> productOptional = productCachePort.get(productId);
            if (productOptional.isEmpty()) {
                String errorMessage = String.format("Product could not be found in cache by product id: %d and order id: %d", productId, item.orderId());
                failureMessages.add(errorMessage);
                logger.error(errorMessage);
                continue;
            }

            paybackQuantityToCache(item, productOptional.get(), productId);
        }
        return failureMessages.isEmpty() ? InventoryStatus.AVAILABLE : InventoryStatus.NON_AVAILABLE;
    }

    private void paybackQuantityToCache(OrderItem item, CachedProduct cachedProduct, Long productId) {
        Quantity oldTempQuantity = new Quantity(cachedProduct.getTempQuantity());
        Quantity newTempQuantity = oldTempQuantity.add(item.quantity());
        cachedProduct.setTempQuantity(newTempQuantity.value());
        productCachePort.put(productId, cachedProduct);
        logger.info("Item quantity payback to cache by productId: {}", productId);
    }

    private OrderOutbox buildOrderOutbox(InventoryRequest inventoryRequest, InventoryStatus inventoryStatus, List<String> failureMessages) {
        OrderOutboxPayload orderOutboxPayload = buildPayload(inventoryRequest, failureMessages, inventoryStatus);
        OrderOutbox orderOutbox = OrderOutbox.builder()
                .sagaId(inventoryRequest.sagaId())
                .payload(jsonPort.convertDataToJson(orderOutboxPayload))
                .outboxStatus(OutboxStatus.STARTED)
                .build();
        return orderOutbox;
    }

    private OrderOutboxPayload buildPayload(InventoryRequest inventoryRequest, List<String> failureMessages, InventoryStatus inventoryStatus) {

        return new OrderOutboxPayload(inventoryRequest, inventoryStatus, failureMessages);
    }
}
