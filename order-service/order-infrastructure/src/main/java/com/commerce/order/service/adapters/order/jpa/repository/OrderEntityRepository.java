package com.commerce.order.service.adapters.order.jpa.repository;

import com.commerce.order.service.adapters.order.jpa.entity.OrderEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @Author mselvi
 * @Created 05.03.2024
 */

@Repository
public interface OrderEntityRepository extends JpaRepository<OrderEntity,Long> {
}
