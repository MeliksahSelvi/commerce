package com.commerce.inventory.service.adapters.inventory.jpa.repository;

import com.commerce.inventory.service.adapters.inventory.jpa.entity.ProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @Author mselvi
 * @Created 07.03.2024
 */

@Repository
public interface ProductEntityRepository extends JpaRepository<ProductEntity, Long> {
}
