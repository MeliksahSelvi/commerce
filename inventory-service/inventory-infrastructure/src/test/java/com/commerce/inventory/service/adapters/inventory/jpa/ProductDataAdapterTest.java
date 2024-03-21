package com.commerce.inventory.service.adapters.inventory.jpa;

import com.commerce.inventory.service.adapters.inventory.jpa.entity.ProductEntity;
import com.commerce.inventory.service.adapters.inventory.jpa.repository.ProductEntityRepository;
import com.commerce.inventory.service.common.valueobject.Availability;
import com.commerce.inventory.service.common.valueobject.Money;
import com.commerce.inventory.service.common.valueobject.Quantity;
import com.commerce.inventory.service.inventory.entity.Product;
import com.commerce.inventory.service.inventory.usecase.ProductRetrieve;
import com.commerce.inventory.service.inventory.usecase.ProductRetrieveAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * @Author mselvi
 * @Created 21.03.2024
 */

@ExtendWith(MockitoExtension.class)
class ProductDataAdapterTest {

    @InjectMocks
    private ProductDataAdapter productDataAdapter;

    @Mock
    private ProductEntityRepository productEntityRepository;

    @Test
    void should_findAll() {
        //given
        var productRetrieveAll = new ProductRetrieveAll(Optional.of(0), Optional.of(1));
        var pageRequest = PageRequest.of(productRetrieveAll.page().get(), productRetrieveAll.size().get());
        var page = new PageImpl<>(buildProductEntities());
        when(productEntityRepository.findAll(pageRequest)).thenReturn(page);

        //when
        var productList = productDataAdapter.findAll(productRetrieveAll);

        //then
        assertEquals(productList.size(), page.getSize());
        assertEquals(productRetrieveAll.size().get(), productList.size());
        assertEquals(productRetrieveAll.size().get(), page.getSize());
        assertNotEquals(Collections.emptyList().size(), page.getSize());
        assertNotEquals(Collections.emptyList().size(), productList.size());
    }

    @Test
    void should_findAll_empty() {
        //given
        var productRetrieveAll = new ProductRetrieveAll(Optional.of(0), Optional.of(1));
        var pageRequest = PageRequest.of(productRetrieveAll.page().get(), productRetrieveAll.size().get());
        var page = new PageImpl<>(new ArrayList<ProductEntity>());
        when(productEntityRepository.findAll(pageRequest)).thenReturn(page);

        //when
        var productList = productDataAdapter.findAll(productRetrieveAll);

        //then
        assertEquals(Collections.EMPTY_LIST.size(), productList.size());
        assertEquals(Collections.EMPTY_LIST.size(), page.getSize());
        assertNotEquals(Collections.EMPTY_LIST.size(), productRetrieveAll.size());
    }

    @Test
    void should_findById() {
        //given
        var retrieve = new ProductRetrieve(1L);
        when(productEntityRepository.findById(retrieve.productId())).thenReturn(Optional.of(buildProductEntity()));

        //when
        var customerOptional = productDataAdapter.findById(retrieve);

        //then
        assertTrue(customerOptional.isPresent());
        assertEquals(retrieve.productId(), customerOptional.get().getId());
    }

    @Test
    void should_findById_empty() {
        //given
        var retrieve = new ProductRetrieve(1L);
        when(productEntityRepository.findById(retrieve.productId())).thenReturn(Optional.empty());

        //when
        var customerOptional = productDataAdapter.findById(retrieve);

        //then
        assertTrue(customerOptional.isEmpty());
    }

    @Test
    void should_save() {
        //given
        var product = buildProduct();
        var productEntity = mock(ProductEntity.class);
        when(productEntityRepository.save(any())).thenReturn(productEntity);
        when(productEntity.toModel()).thenReturn(product);

        //when
        var savedProduct = productDataAdapter.save(product);

        //then
        assertEquals(product.getId(),savedProduct.getId());
        assertEquals(product.getName(),savedProduct.getName());
        assertEquals(product.getPrice(),savedProduct.getPrice());
        assertEquals(product.getQuantity(),savedProduct.getQuantity());
        assertEquals(product.getAvailability(),savedProduct.getAvailability());
    }

    private Product buildProduct() {
        return Product.builder()
                .id(1L)
                .name("identity1")
                .price(new Money(BigDecimal.TEN))
                .quantity(new Quantity(3))
                .availability(new Availability(true))
                .build();
    }

    private List<ProductEntity> buildProductEntities() {
        return List.of(buildProductEntity());
    }

    private ProductEntity buildProductEntity() {
        var productEntity = new ProductEntity();
        productEntity.setId(1L);
        productEntity.setName("name");
        productEntity.setPrice(BigDecimal.valueOf(100));
        productEntity.setQuantity(10);
        productEntity.setAvailability(true);
        return productEntity;
    }
}
