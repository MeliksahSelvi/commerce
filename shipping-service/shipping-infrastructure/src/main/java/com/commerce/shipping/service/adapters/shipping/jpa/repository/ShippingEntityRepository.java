package com.commerce.shipping.service.adapters.shipping.jpa.repository;

import com.commerce.shipping.service.adapters.shipping.jpa.entity.ShippingEntity;
import com.commerce.shipping.service.common.valueobject.DeliveryStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * @Author mselvi
 * @Created 09.03.2024
 */

@Repository
public interface ShippingEntityRepository extends JpaRepository<ShippingEntity,Long> {

    Optional<ShippingEntity> findByOrderIdAndDeliveryStatus(Long orderId, DeliveryStatus deliveryStatus);

    Optional<ShippingEntity> findByOrderId(Long orderId);
}
