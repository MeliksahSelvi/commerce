package com.commerce.customer.service.adapters.jpa.repository;

import com.commerce.customer.service.adapters.jpa.entity.CustomerEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @Author mselvi
 * @Created 10.03.2024
 */

@Repository
public interface CustomerEntityRepository extends JpaRepository<CustomerEntity, Long> {

    boolean existsById(Long id);
}
