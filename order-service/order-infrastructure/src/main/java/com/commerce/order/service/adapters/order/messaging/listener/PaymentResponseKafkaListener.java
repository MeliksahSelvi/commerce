package com.commerce.order.service.adapters.order.messaging.listener;

import com.commerce.kafka.consumer.KafkaConsumer;
import com.commerce.kafka.model.PaymentResponseAvroModel;
import com.commerce.order.service.common.saga.SagaStep;
import com.commerce.order.service.common.valueobject.Money;
import com.commerce.order.service.common.valueobject.PaymentStatus;
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
public class PaymentResponseKafkaListener implements KafkaConsumer<PaymentResponseAvroModel> {

    private static final Logger logger = LoggerFactory.getLogger(PaymentResponseKafkaListener.class);
    private final SagaStep<PaymentResponse> paymentResponseSagaStep;

    public PaymentResponseKafkaListener(SagaStep<PaymentResponse> paymentResponseSagaStep) {
        this.paymentResponseSagaStep = paymentResponseSagaStep;
    }


    @Override
    @KafkaListener(id = "${kafka-consumer-config.payment-consumer-group-id}",
            topics = "${order-service.payment-response-topic-name}")
    public void receive(@Payload List<PaymentResponseAvroModel> messages,
                        @Header(KafkaHeaders.RECEIVED_KEY) List<String> keys,
                        @Header(KafkaHeaders.RECEIVED_PARTITION) List<Integer> partitions,
                        @Header(KafkaHeaders.OFFSET) List<Long> offsets) {

        logger.info("{} number of payment requests received with keys:{}, partitions:{} and offsets:{}",
                messages.size(), keys, partitions, offsets);

        for (PaymentResponseAvroModel message : messages) {

            try {
                PaymentResponse paymentResponse = buildPaymentResponse(message);
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

    private PaymentResponse buildPaymentResponse(PaymentResponseAvroModel avroModel) {
        return new PaymentResponse(UUID.fromString(avroModel.getSagaId()), avroModel.getOrderId(), avroModel.getPaymentId(), avroModel.getCustomerId(),
                new Money(avroModel.getCost()), PaymentStatus.valueOf(avroModel.getPaymentStatus().name()), avroModel.getFailureMessages());
    }
}
