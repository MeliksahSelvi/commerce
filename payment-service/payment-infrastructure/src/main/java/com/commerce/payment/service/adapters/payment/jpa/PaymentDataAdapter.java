package com.commerce.payment.service.adapters.payment.jpa;

import com.commerce.payment.service.adapters.payment.jpa.entity.PaymentEntity;
import com.commerce.payment.service.adapters.payment.jpa.repository.PaymentEntityRepository;
import com.commerce.payment.service.payment.entity.Payment;
import com.commerce.payment.service.payment.port.jpa.PaymentDataPort;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * @Author mselvi
 * @Created 08.03.2024
 */

@Service
public class PaymentDataAdapter implements PaymentDataPort {

    private static final Logger logger = LoggerFactory.getLogger(PaymentDataAdapter.class);
    private final PaymentEntityRepository repository;

    public PaymentDataAdapter(PaymentEntityRepository repository) {
        this.repository = repository;
    }

    @Override
    public Payment save(Payment payment) {
        var paymentEntity = new PaymentEntity();
        paymentEntity.setId(payment.getId());
        paymentEntity.setOrderId(payment.getOrderId());
        paymentEntity.setCustomerId(payment.getCustomerId());
        paymentEntity.setCost(payment.getCost().amount());
        paymentEntity.setPaymentStatus(payment.getPaymentStatus());
        return repository.save(paymentEntity).toModel();
    }

    @Override
    public Optional<Payment> findByOrderId(Long orderId) {
        Optional<PaymentEntity> paymentEntityOptional = repository.findByOrderId(orderId);
        return paymentEntityOptional.map(PaymentEntity::toModel);
    }
}
