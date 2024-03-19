package com.commerce.order.service.adapters.order.messaging.publisher;

import com.commerce.kafka.model.OrderPaymentStatus;
import com.commerce.kafka.model.PaymentRequestAvroModel;
import com.commerce.kafka.producer.KafkaProducer;
import com.commerce.order.service.common.messaging.kafka.helper.KafkaHelper;
import com.commerce.order.service.common.outbox.OutboxStatus;
import com.commerce.order.service.order.port.messaging.output.PaymentRequestMessagePublisher;
import com.commerce.order.service.outbox.entity.PaymentOutbox;
import com.commerce.order.service.outbox.entity.PaymentOutboxPayload;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.UUID;
import java.util.function.BiConsumer;

/**
 * @Author mselvi
 * @Created 06.03.2024
 */

@Component
public class PaymentRequestKafkaPublisher implements PaymentRequestMessagePublisher {

    private static final Logger logger = LoggerFactory.getLogger(PaymentRequestKafkaPublisher.class);
    private final KafkaProducer<String, PaymentRequestAvroModel> kafkaProducer;
    private final KafkaHelper kafkaHelper;

    @Value("${order-service.payment-request-topic-name}")
    private String paymentRequestTopicName;

    public PaymentRequestKafkaPublisher(KafkaProducer<String, PaymentRequestAvroModel> kafkaProducer, KafkaHelper kafkaHelper) {
        this.kafkaProducer = kafkaProducer;
        this.kafkaHelper = kafkaHelper;
    }

    @Override
    public void publish(PaymentOutbox paymentOutbox, BiConsumer<PaymentOutbox, OutboxStatus> outboxCallback) {
        PaymentOutboxPayload paymentOutboxPayload = kafkaHelper.getPayload(paymentOutbox.getPayload(), PaymentOutboxPayload.class);
        UUID sagaId = paymentOutboxPayload.sagaId();
        Long orderId = paymentOutboxPayload.orderId();

        logger.info("Received PaymentOutbox for order id: {} and saga id: {}", orderId, sagaId);

        try {
            PaymentRequestAvroModel avroModel = buildAvroModel(paymentOutboxPayload);
            kafkaProducer.send(paymentRequestTopicName, sagaId.toString(), avroModel,
                    kafkaHelper.getKafkaCallback(avroModel, paymentOutbox, outboxCallback, orderId));

            logger.info("PaymentRequestAvroModel sent to Kafka for order id: {} and saga id: {}", orderId, sagaId);
        } catch (Exception e) {
            logger.error("Error while sending PaymentRequestAvroModel to Kafka with order id: {} and saga id: {} error: {}", orderId, sagaId, e.getMessage());
        }
    }

    private PaymentRequestAvroModel buildAvroModel(PaymentOutboxPayload payload) {
        return PaymentRequestAvroModel.newBuilder()
                .setSagaId(payload.sagaId().toString())
                .setOrderId(payload.orderId())
                .setCustomerId(payload.customerId())
                .setCost(payload.cost())
                .setOrderPaymentStatus(OrderPaymentStatus.valueOf(payload.orderPaymentStatus().name()))
                .build();
    }

}
