package com.commerce.payment.service.adapters.payment.messaging.listener;

import com.commerce.kafka.consumer.KafkaConsumer;
import com.commerce.kafka.model.PaymentRequestAvroModel;
import com.commerce.payment.service.common.valueobject.Money;
import com.commerce.payment.service.common.valueobject.OrderPaymentStatus;
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
public class PaymentRequestKafkaListener implements KafkaConsumer<PaymentRequestAvroModel> {

    private static final Logger logger = LoggerFactory.getLogger(PaymentRequestKafkaListener.class);
    private final PaymentRequestMessageListener paymentRequestMessageListener;

    public PaymentRequestKafkaListener(PaymentRequestMessageListener paymentRequestMessageListener) {
        this.paymentRequestMessageListener = paymentRequestMessageListener;
    }

    @Override
    @KafkaListener(id = "${kafka-consumer-config.payment-consumer-group-id}",
            topics = "${payment-service.payment-request-topic-name}")
    public void receive(@Payload List<PaymentRequestAvroModel> messages,
                        @Header(KafkaHeaders.RECEIVED_KEY) List<String> keys,
                        @Header(KafkaHeaders.RECEIVED_PARTITION) List<Integer> partitions,
                        @Header(KafkaHeaders.OFFSET) List<Long> offsets) {

        logger.info("{} number of messages received with keys:{}, partitions:{} and offsets:{}",
                messages.size(), keys, partitions, offsets);

        for (PaymentRequestAvroModel avroModel : messages) {
            PaymentRequest paymentRequest = buildPaymentRequest(avroModel);
            try {
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

    private PaymentRequest buildPaymentRequest(PaymentRequestAvroModel avroModel) {
        return new PaymentRequest(UUID.fromString(avroModel.getSagaId()), avroModel.getOrderId(), avroModel.getCustomerId(),
                new Money(avroModel.getCost()), OrderPaymentStatus.valueOf(avroModel.getOrderPaymentStatus().name()));
    }
}
