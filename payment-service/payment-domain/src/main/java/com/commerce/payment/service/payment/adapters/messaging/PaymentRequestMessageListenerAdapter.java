package com.commerce.payment.service.payment.adapters.messaging;

import com.commerce.payment.service.common.DomainComponent;
import com.commerce.payment.service.common.exception.PaymentDomainException;
import com.commerce.payment.service.common.exception.PaymentNotFoundException;
import com.commerce.payment.service.common.outbox.OutboxStatus;
import com.commerce.payment.service.common.valueobject.ActivityType;
import com.commerce.payment.service.common.valueobject.PaymentStatus;
import com.commerce.payment.service.outbox.entity.OrderOutbox;
import com.commerce.payment.service.outbox.entity.OrderOutboxPayload;
import com.commerce.payment.service.outbox.port.jpa.OrderOutboxDataPort;
import com.commerce.payment.service.payment.entity.Account;
import com.commerce.payment.service.payment.entity.AccountActivity;
import com.commerce.payment.service.payment.entity.Payment;
import com.commerce.payment.service.payment.port.jpa.AccountActivityDataPort;
import com.commerce.payment.service.payment.port.jpa.AccountDataPort;
import com.commerce.payment.service.payment.port.jpa.PaymentDataPort;
import com.commerce.payment.service.payment.port.messaging.input.PaymentRequestMessageListener;
import com.commerce.payment.service.payment.usecase.PaymentRequest;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author mselvi
 * @Created 07.03.2024
 */

@DomainComponent
public class PaymentRequestMessageListenerAdapter implements PaymentRequestMessageListener {

    private static final Logger logger = LoggerFactory.getLogger(PaymentRequestMessageListenerAdapter.class);
    private final AccountActivityDataPort accountActivityDataPort;
    private final OrderOutboxDataPort orderOutboxDataPort;
    private final AccountDataPort accountDataPort;
    private final PaymentDataPort paymentDataPort;
    private final ObjectMapper objectMapper;

    public PaymentRequestMessageListenerAdapter(AccountDataPort accountDataPort, PaymentDataPort paymentDataPort, AccountActivityDataPort accountActivityDataPort, OrderOutboxDataPort orderOutboxDataPort, ObjectMapper objectMapper) {
        this.accountDataPort = accountDataPort;
        this.paymentDataPort = paymentDataPort;
        this.accountActivityDataPort = accountActivityDataPort;
        this.orderOutboxDataPort = orderOutboxDataPort;
        this.objectMapper = objectMapper;
    }

    @Override
    public void completePayment(PaymentRequest paymentRequest) {
        logger.info("Received payment complete event for order id: {}", paymentRequest.orderId());
        Payment payment = Payment.builder()
                .orderId(paymentRequest.orderId())
                .customerId(paymentRequest.customerId())
                .cost(paymentRequest.cost())
                .build();
        List<String> failureMessages = new ArrayList<>();
        payment.validatePayment(failureMessages);

        Account account = findAccountByCustomerId(paymentRequest.customerId());
        account.validateCurrentBalance(payment, failureMessages);
        account.decreaseCurrentBalance(paymentRequest.cost(), failureMessages);
        AccountActivity accountActivity = AccountActivity.builder()
                .accountId(account.getId())
                .currentBalance(account.getCurrentBalance())
                .activityType(ActivityType.SPEND)
                .cost(paymentRequest.cost())
                .transactionDate(LocalDateTime.now())
                .build();

        PaymentStatus paymentStatus;//todo refactor bad code
        if (failureMessages.isEmpty()) {
            paymentStatus = PaymentStatus.COMPLETED;
            accountDataPort.save(account);
            accountActivityDataPort.save(accountActivity);
        } else {
            paymentStatus = PaymentStatus.FAILED;
        }
        payment.setPaymentStatus(paymentStatus);
        Payment savedPayment = paymentDataPort.save(payment);


        OrderOutboxPayload orderOutboxPayload = new OrderOutboxPayload(savedPayment.getId(), paymentRequest.orderId(),
                paymentRequest.customerId(), paymentRequest.cost().amount(), paymentStatus, failureMessages);
        OrderOutbox orderOutbox = OrderOutbox.builder()
                .sagaId(paymentRequest.sagaId())
                .payload(createPayload(orderOutboxPayload))
                .paymentStatus(paymentStatus)
                .outboxStatus(OutboxStatus.STARTED)
                .build();
        OrderOutbox savedOutbox = orderOutboxDataPort.save(orderOutbox);
        if (savedOutbox == null) {
            throw new PaymentDomainException(String.format("Could not save OrderOutbox with outbox id: %d",
                    orderOutbox.getId()));
        }
    }

    private Account findAccountByCustomerId(Long customerId) {
        return accountDataPort.findByCustomerId(customerId)
                .orElseThrow(() -> new PaymentDomainException(String.format("Could not find Account for Customer %d", customerId)));
    }

    private String createPayload(OrderOutboxPayload orderOutboxPayload) {
        try {
            return objectMapper.writeValueAsString(orderOutboxPayload);
        } catch (JsonProcessingException e) {
            throw new PaymentDomainException(String.format("Could not create OrderOutboxPayload object for order id: %d", orderOutboxPayload.orderId()), e);
        }
    }

    @Override
    public void cancelPayment(PaymentRequest paymentRequest) {

        logger.info("Received payment rollback event for order id: {}", paymentRequest.orderId());
        Payment payment = paymentDataPort.findByOrderId(paymentRequest.orderId())
                .orElseThrow(() -> new PaymentNotFoundException(String.format("Payment for orderId: %d could not be found!", paymentRequest.orderId())));
        List<String> failureMessages = new ArrayList<>();
        payment.validatePayment(failureMessages);

        Account account = findAccountByCustomerId(paymentRequest.customerId());
        account.increaseCurrentBalance(paymentRequest.cost());

        AccountActivity accountActivity = AccountActivity.builder()
                .accountId(account.getId())
                .currentBalance(account.getCurrentBalance())
                .activityType(ActivityType.PAYBACK)
                .cost(paymentRequest.cost())
                .transactionDate(LocalDateTime.now())
                .build();

        PaymentStatus paymentStatus;
        if (failureMessages.isEmpty()) {
            paymentStatus = PaymentStatus.CANCELLED;
            accountDataPort.save(account);
            accountActivityDataPort.save(accountActivity);
        } else {
            paymentStatus = PaymentStatus.FAILED;
        }
        payment.setPaymentStatus(paymentStatus);
        Payment savedPayment = paymentDataPort.save(payment);

        OrderOutboxPayload orderOutboxPayload = new OrderOutboxPayload(savedPayment.getId(), paymentRequest.orderId(),
                paymentRequest.customerId(), paymentRequest.cost().amount(), paymentStatus, failureMessages);
        OrderOutbox orderOutbox = OrderOutbox.builder()
                .sagaId(paymentRequest.sagaId())
                .payload(createPayload(orderOutboxPayload))
                .paymentStatus(paymentStatus)
                .outboxStatus(OutboxStatus.STARTED)
                .build();
        OrderOutbox savedOutbox = orderOutboxDataPort.save(orderOutbox);
        if (savedOutbox == null) {
            throw new PaymentDomainException(String.format("Could not save OrderOutbox with outbox id: %d",
                    orderOutbox.getId()));
        }

    }
}
