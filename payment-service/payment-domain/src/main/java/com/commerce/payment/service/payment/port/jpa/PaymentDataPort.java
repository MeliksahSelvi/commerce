package com.commerce.payment.service.payment.port.jpa;

import com.commerce.payment.service.payment.model.Payment;

import java.util.Optional;

/**
 * @Author mselvi
 * @Created 07.03.2024
 */

public interface PaymentDataPort {

    Payment save(Payment payment);

    Optional<Payment> findByOrderId(Long orderId);
}
