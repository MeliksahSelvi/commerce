package com.commerce.payment.service.payment.adapters.messaging.helper;

import com.commerce.payment.service.common.DomainComponent;
import com.commerce.payment.service.common.exception.AccountNotFoundException;
import com.commerce.payment.service.common.exception.PaymentNotFoundException;
import com.commerce.payment.service.common.outbox.OutboxStatus;
import com.commerce.payment.service.common.valueobject.ActivityType;
import com.commerce.payment.service.common.valueobject.Money;
import com.commerce.payment.service.outbox.entity.OrderOutbox;
import com.commerce.payment.service.outbox.entity.OrderOutboxPayload;
import com.commerce.payment.service.outbox.port.jpa.OrderOutboxDataPort;
import com.commerce.payment.service.payment.entity.Account;
import com.commerce.payment.service.payment.entity.AccountActivity;
import com.commerce.payment.service.payment.entity.Payment;
import com.commerce.payment.service.payment.port.jpa.AccountActivityDataPort;
import com.commerce.payment.service.payment.port.jpa.AccountDataPort;
import com.commerce.payment.service.payment.port.jpa.PaymentDataPort;
import com.commerce.payment.service.payment.port.json.JsonPort;
import com.commerce.payment.service.payment.usecase.PaymentRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * @Author mselvi
 * @Created 19.03.2024
 */

@DomainComponent
public class PaymentRequestListenerHelper {

    private static final Logger logger = LoggerFactory.getLogger(PaymentRequestListenerHelper.class);
    private final AccountActivityDataPort accountActivityDataPort;
    private final OrderOutboxDataPort orderOutboxDataPort;
    private final AccountDataPort accountDataPort;
    private final PaymentDataPort paymentDataPort;
    private final JsonPort jsonPort;

    public PaymentRequestListenerHelper(AccountActivityDataPort accountActivityDataPort, OrderOutboxDataPort orderOutboxDataPort,
                                        AccountDataPort accountDataPort, PaymentDataPort paymentDataPort, JsonPort jsonPort) {
        this.accountActivityDataPort = accountActivityDataPort;
        this.orderOutboxDataPort = orderOutboxDataPort;
        this.accountDataPort = accountDataPort;
        this.paymentDataPort = paymentDataPort;
        this.jsonPort = jsonPort;
    }


    @Transactional
    public List<String> completePayment(PaymentRequest paymentRequest) {
        UUID sagaId = paymentRequest.sagaId();
        Long orderId = paymentRequest.orderId();
        Long customerId = paymentRequest.customerId();
        Money cost = paymentRequest.cost();

        logger.info("Received payment request for order id: {}", orderId);

        List<String> failureMessages = new ArrayList<>();

        Account account = findAccountByCustomerId(customerId);
        account.validateCurrentBalance(cost, customerId, failureMessages);
        account.decreaseCurrentBalance(cost, failureMessages);

        AccountActivity accountActivity = buildAccountActivity(account, ActivityType.SPEND, cost, failureMessages);

        Payment payment = buildPayment(paymentRequest, failureMessages);
        logger.info("Payment initiated for orderId: {}", orderId);

        accountDataPort.save(account);
        accountActivityDataPort.save(accountActivity);
        Payment savedPayment = paymentDataPort.save(payment);
        logger.info("Payment persisted with orderId: {}", orderId);

        OrderOutbox orderOutbox = buildOrderOutbox(sagaId, savedPayment, failureMessages);
        orderOutboxDataPort.save(orderOutbox);
        logger.info("OrderOutbox persisted for payment request with paymentId", savedPayment.getId());
        return failureMessages;
    }

    private Payment buildPayment(PaymentRequest paymentRequest, List<String> failureMessages) {
        Payment payment = Payment.builder()
                .orderId(paymentRequest.orderId())
                .customerId(paymentRequest.customerId())
                .cost(paymentRequest.cost())
                .build();
        payment.validatePayment(failureMessages);
        payment.initializeStatus(failureMessages);
        return payment;
    }

    @Transactional
    public List<String> cancelPayment(PaymentRequest paymentRequest) {
        Long orderId = paymentRequest.orderId();
        Money cost = paymentRequest.cost();

        logger.info("Received payment rollback for order id: {}", orderId);

        List<String> failureMessages = new ArrayList<>();

        Payment payment = findPayment(orderId);
        payment.validatePayment(failureMessages);
        payment.cancelPayment(failureMessages);
        logger.info("Payment status updated for orderId: {}", orderId);

        Account account = findAccountByCustomerId(paymentRequest.customerId());
        account.increaseCurrentBalance(cost, failureMessages);

        AccountActivity accountActivity = buildAccountActivity(account, ActivityType.PAYBACK, cost, failureMessages);

        accountDataPort.save(account);
        accountActivityDataPort.save(accountActivity);
        Payment savedPayment = paymentDataPort.save(payment);
        logger.info("Payment updated for orderId: {}", orderId);

        OrderOutbox orderOutbox = buildOrderOutbox(paymentRequest.sagaId(), savedPayment, failureMessages);
        orderOutboxDataPort.save(orderOutbox);
        logger.info("PaymentOutbox persisted for payment rollback with orderId: {}", orderId);
        return failureMessages;
    }

    private Payment findPayment(Long orderId) {
        return paymentDataPort.findByOrderId(orderId)
                .orElseThrow(() -> new PaymentNotFoundException(String.format("Payment for orderId: %d could not be found!", orderId)));
    }

    private Account findAccountByCustomerId(Long customerId) {
        return accountDataPort.findByCustomerId(customerId)
                .orElseThrow(() -> new AccountNotFoundException(String.format("Could not find Account for Customer %d", customerId)));
    }

    private AccountActivity buildAccountActivity(Account account, ActivityType activityType, Money cost, List<String> failureMessages) {
        AccountActivity accountActivity = AccountActivity.builder()
                .accountId(account.getId())
                .currentBalance(account.getCurrentBalance())
                .activityType(activityType)
                .cost(cost)
                .transactionDate(LocalDateTime.now())
                .build();
        accountActivity.initializeActivityType(activityType, failureMessages);
        return accountActivity;
    }

    private OrderOutbox buildOrderOutbox(UUID sagaId, Payment payment, List<String> failureMessages) {
        OrderOutboxPayload orderOutboxPayload = new OrderOutboxPayload(payment, failureMessages);
        return OrderOutbox.builder()
                .sagaId(sagaId)
                .payload(jsonPort.convertDataToJson(orderOutboxPayload))
                .outboxStatus(OutboxStatus.STARTED)
                .build();
    }
}
