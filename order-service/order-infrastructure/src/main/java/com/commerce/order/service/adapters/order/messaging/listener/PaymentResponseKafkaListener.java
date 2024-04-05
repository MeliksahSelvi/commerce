package com.commerce.order.service.adapters.order.messaging.listener;

import com.commerce.order.service.common.messaging.kafka.consumer.KafkaConsumer;
import com.commerce.order.service.common.messaging.kafka.model.PaymentResponseKafkaModel;
import com.commerce.order.service.common.saga.SagaStep;
import com.commerce.order.service.common.valueobject.Money;
import com.commerce.order.service.order.port.json.JsonPort;
import com.commerce.order.service.order.usecase.PaymentResponse;
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
 * @Created 06.03.2024
 */

@Component
public class PaymentResponseKafkaListener implements KafkaConsumer {

    private static final Logger logger = LoggerFactory.getLogger(PaymentResponseKafkaListener.class);
    private final SagaStep<PaymentResponse> paymentResponseSagaStep;
    private final JsonPort jsonPort;

    public PaymentResponseKafkaListener(SagaStep<PaymentResponse> paymentResponseSagaStep, JsonPort jsonPort) {
        this.paymentResponseSagaStep = paymentResponseSagaStep;
        this.jsonPort = jsonPort;
    }


    @Override
    @KafkaListener(id = "${kafka-consumer-config.payment-consumer-group-id}",
            topics = "${order-service.payment-response-topic-name}")
    public void receive(@Payload List<String> messages,
                        @Header(KafkaHeaders.RECEIVED_KEY) List<String> keys,
                        @Header(KafkaHeaders.RECEIVED_PARTITION) List<Integer> partitions,
                        @Header(KafkaHeaders.OFFSET) List<Long> offsets) {

        logger.info("{} number of messages received with keys:{}, partitions:{} and offsets:{}",
                messages.size(), keys, partitions, offsets);

        for (String message : messages) {

            try {
                PaymentResponseKafkaModel kafkaModel = jsonPort.exractDataFromJson(message, PaymentResponseKafkaModel.class);
                PaymentResponse paymentResponse = buildPaymentResponse(kafkaModel);
                switch (paymentResponse.paymentStatus()) {
                    case COMPLETED -> {
                        logger.info("Successful payment for order id: {}", paymentResponse.orderId());
                        paymentResponseSagaStep.process(paymentResponse);
                    }
                    case CANCELLED, FAILED -> {
                        logger.info("Unsuccessful payment for order id: {}", paymentResponse.orderId());
                        paymentResponseSagaStep.rollback(paymentResponse);
                    }
                }
            } catch (Exception e) {
                logger.error(e.getMessage());
            }
        }
    }

    private PaymentResponse buildPaymentResponse(PaymentResponseKafkaModel kafkaModel) {
        return new PaymentResponse(UUID.fromString(kafkaModel.sagaId()), kafkaModel.orderId(), kafkaModel.paymentId(), kafkaModel.customerId(),
                new Money(kafkaModel.cost()), kafkaModel.paymentStatus(), kafkaModel.failureMessages());
    }
}
