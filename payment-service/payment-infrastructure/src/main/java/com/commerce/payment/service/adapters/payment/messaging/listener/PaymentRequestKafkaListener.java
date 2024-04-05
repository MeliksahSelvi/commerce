package com.commerce.payment.service.adapters.payment.messaging.listener;

import com.commerce.payment.service.common.messaging.kafka.consumer.KafkaConsumer;
import com.commerce.payment.service.common.messaging.kafka.model.PaymentRequestKafkaModel;
import com.commerce.payment.service.common.valueobject.Money;
import com.commerce.payment.service.common.valueobject.OrderPaymentStatus;
import com.commerce.payment.service.payment.port.json.JsonPort;
import com.commerce.payment.service.payment.port.messaging.input.PaymentRequestMessageListener;
import com.commerce.payment.service.payment.usecase.PaymentRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

/**
 * @Author mselvi
 * @Created 07.03.2024
 */

@Component
public class PaymentRequestKafkaListener implements KafkaConsumer {

    private static final Logger logger = LoggerFactory.getLogger(PaymentRequestKafkaListener.class);
    private final PaymentRequestMessageListener paymentRequestMessageListener;
    private final JsonPort jsonPort;

    public PaymentRequestKafkaListener(PaymentRequestMessageListener paymentRequestMessageListener, JsonPort jsonPort) {
        this.paymentRequestMessageListener = paymentRequestMessageListener;
        this.jsonPort = jsonPort;
    }

    @Override
    @KafkaListener(id = "${kafka-consumer-config.payment-consumer-group-id}",
            topics = "${payment-service.payment-request-topic-name}")
    public void receive(@Payload List<String> messages,
                        @Header(KafkaHeaders.RECEIVED_KEY) List<String> keys,
                        @Header(KafkaHeaders.RECEIVED_PARTITION) List<Integer> partitions,
                        @Header(KafkaHeaders.OFFSET) List<Long> offsets) {

        logger.info("{} number of messages received with keys:{}, partitions:{} and offsets:{}",
                messages.size(), keys, partitions, offsets);

        for (String message : messages) {
            try {
                PaymentRequestKafkaModel kafkaModel = jsonPort.exractDataFromJson(message, PaymentRequestKafkaModel.class);
                PaymentRequest paymentRequest = buildPaymentRequest(kafkaModel);
                switch (paymentRequest.orderPaymentStatus()) {
                    case PENDING -> {
                        logger.info("Processing payment for order id: {}", paymentRequest.orderId());
                        paymentRequestMessageListener.completePayment(paymentRequest);
                    }
                    case CANCELLED -> {
                        logger.info("Cancelling payment for order id: {}", paymentRequest.orderId());
                        paymentRequestMessageListener.cancelPayment(paymentRequest);
                    }
                }
            } catch (Exception e) {
                logger.error(e.getMessage());
            }
        }
    }

    private PaymentRequest buildPaymentRequest(PaymentRequestKafkaModel kafkaModel) {
        return new PaymentRequest(UUID.fromString(kafkaModel.sagaId()), kafkaModel.orderId(), kafkaModel.customerId(),
                new Money(kafkaModel.cost()), OrderPaymentStatus.valueOf(kafkaModel.orderPaymentStatus().name()));
    }
}
