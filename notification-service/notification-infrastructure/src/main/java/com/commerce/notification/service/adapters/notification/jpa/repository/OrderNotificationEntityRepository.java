package com.commerce.notification.service.adapters.notification.jpa.repository;

import com.commerce.notification.service.adapters.notification.jpa.entity.OrderNotificationEntity;
import com.commerce.notification.service.common.valueobject.NotificationStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * @Author mselvi
 * @Created 08.03.2024
 */

@Repository
public interface OrderNotificationEntityRepository extends JpaRepository<OrderNotificationEntity, Long> {

    Optional<OrderNotificationEntity> findByOrderIdAndNotificationStatus(Long orderId, NotificationStatus notificationStatus);
}
