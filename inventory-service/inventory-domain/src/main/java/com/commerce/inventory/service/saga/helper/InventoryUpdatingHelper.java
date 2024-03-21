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
public class InventoryUpdatingHelper {

    private static final Logger logger = LoggerFactory.getLogger(InventoryUpdatingHelper.class);
    private final OrderOutboxDataPort orderOutboxDataPort;
    private final ProductCachePort productCachePort;
    private final ProductDataPort productDataPort;
    private final JsonPort jsonPort;

    public InventoryUpdatingHelper(OrderOutboxDataPort orderOutboxDataPort, ProductCachePort productCachePort,
                                   ProductDataPort productDataPort, JsonPort jsonPort) {
        this.orderOutboxDataPort = orderOutboxDataPort;
        this.productCachePort = productCachePort;
        this.productDataPort = productDataPort;
        this.jsonPort = jsonPort;
    }

    @Transactional
    public void process(InventoryRequest inventoryRequest) {
        List<OrderItem> items = inventoryRequest.items();

        List<String> failureMessages = checkCacheByItems(items);

        logger.info("Items quantity updating actions started on cache");
        InventoryStatus inventoryStatus = updatingActionsOnCache(items, failureMessages);
        logger.info("Items quantity updating actions finished on cache");

        OrderOutbox orderOutbox = buildOrderOutbox(inventoryRequest, inventoryStatus, failureMessages);
        orderOutboxDataPort.save(orderOutbox);
        logger.info("OrderOutbox persisted for inventory updating action by sagaId: {}", inventoryRequest.sagaId());
    }

    private List<String> checkCacheByItems(List<OrderItem> items) {
        List<String> failureMessages = new ArrayList<>();
        items.stream()
                .filter(orderItem -> {
                    Long productId = orderItem.productId();
                    Optional<CachedProduct> productOptional = productCachePort.get(productId);
                    return productOptional.isEmpty() || (productOptional.isPresent() && isQuantityNotEnoughForOrderItem(orderItem, productOptional));
                })
                .map(orderItem -> String.format("Product has already solded by productId: %d", orderItem.productId()))
                .forEach(failureMessages::add);
        return failureMessages;
    }

    private boolean isQuantityNotEnoughForOrderItem(OrderItem orderItem, Optional<CachedProduct> productOptional) {
        CachedProduct cachedProduct = productOptional.get();
        Quantity baseQuantity = new Quantity(cachedProduct.getBaseQuantity());

        return baseQuantity.isLowerThan(orderItem.quantity());
    }

    private InventoryStatus updatingActionsOnCache(List<OrderItem> items, List<String> failureMessages) {
        if (!failureMessages.isEmpty()) {
            return InventoryStatus.NON_AVAILABLE;
        }

        for (OrderItem item : items) {
            Long productId = item.productId();

            Optional<Product> productOptional = productDataPort.findById(new ProductRetrieve(productId));
            if (productOptional.isEmpty()) {
                String errorMessage = String.format("Product could not find by productId: %d", productId);
                failureMessages.add(errorMessage);
                logger.error(errorMessage);
                break;
            }

            Optional<CachedProduct> cachedProductOptional = productCachePort.get(productId);
            if (cachedProductOptional.isEmpty()) {
                String errorMessage = String.format("Product could not find on cache by productId: %d", productId);
                failureMessages.add(errorMessage);
                logger.error(errorMessage);
                break;
            }

            Quantity orderQuantity = item.quantity();
            Product savedProduct = updateProduct(productOptional.get(), orderQuantity, failureMessages);
            logger.info("Product updated for inventory updating action by productId: {}", productId);

            updateCache(cachedProductOptional.get(), savedProduct, orderQuantity, productId);
        }
        return failureMessages.isEmpty() ? InventoryStatus.AVAILABLE : InventoryStatus.NON_AVAILABLE;
    }

    private Product updateProduct(Product product, Quantity orderQuantity, List<String> failureMessages) {
        product.decreaseQuantity(orderQuantity, failureMessages);
        return productDataPort.save(product);
    }

    private void updateCache(CachedProduct cachedProduct, Product product, Quantity orderQuantity, Long productId) {
        Quantity baseQuantity = new Quantity(cachedProduct.getBaseQuantity());
        Quantity tempQuantity = new Quantity(cachedProduct.getTempQuantity());
        Quantity diffQuantity = baseQuantity.substract(tempQuantity);

        if (diffQuantity.isEqual(orderQuantity)) {
            productCachePort.remove(productId);
            logger.info("Product removed from cache by productId: {}", productId);
        } else if (diffQuantity.isGreaterThan(orderQuantity)) {
            cachedProduct.setBaseQuantity(product.getQuantity().value());
            productCachePort.put(productId, cachedProduct);
            logger.info("Product updated on cache by productId: {}", productId);
        }
    }

    @Transactional
    public void rollback(InventoryRequest inventoryRequest) {
        List<String> failureMessages = new ArrayList<>();

        Long orderId = inventoryRequest.orderId();
        logger.info("Product updating action started by orderId: {}", orderId);
        InventoryStatus inventoryStatus = updateProductsByItems(inventoryRequest.items(), failureMessages);
        logger.info("Product updating action finished by orderId: {}", orderId);

        OrderOutbox orderOutbox = buildOrderOutbox(inventoryRequest, inventoryStatus, failureMessages);
        orderOutboxDataPort.save(orderOutbox);
        logger.info("OrderOutbox persisted for inventory updating rollback action by sagaId: {}", inventoryRequest.sagaId());
    }

    private InventoryStatus updateProductsByItems(List<OrderItem> items, List<String> failureMessages) {
        for (OrderItem item : items) {
            Long productId = item.productId();

            Optional<Product> productOptional = productDataPort.findById(new ProductRetrieve(productId));
            if (productOptional.isEmpty()) {
                String errorMessage = String.format("Product could not be found by product id: %d and order id: %d", productId, item.orderId());
                failureMessages.add(errorMessage);
                logger.error(errorMessage);
                break;
            }
            Product product = productOptional.get();
            product.increaseQuantity(item.quantity());
            productDataPort.save(product);
        }
        return failureMessages.isEmpty() ? InventoryStatus.AVAILABLE : InventoryStatus.NON_AVAILABLE;
    }

    private OrderOutbox buildOrderOutbox(InventoryRequest inventoryRequest, InventoryStatus inventoryStatus, List<String> failureMessages) {
        OrderOutboxPayload orderOutboxPayload = buildPayload(inventoryRequest, inventoryStatus, failureMessages);
        OrderOutbox orderOutbox = OrderOutbox.builder()
                .sagaId(inventoryRequest.sagaId())
                .payload(jsonPort.convertDataToJson(orderOutboxPayload))
                .outboxStatus(OutboxStatus.STARTED)
                .build();
        return orderOutbox;
    }

    private OrderOutboxPayload buildPayload(InventoryRequest inventoryRequest, InventoryStatus inventoryStatus, List<String> failureMessages) {

        return new OrderOutboxPayload(inventoryRequest, inventoryStatus, failureMessages);
    }
}
