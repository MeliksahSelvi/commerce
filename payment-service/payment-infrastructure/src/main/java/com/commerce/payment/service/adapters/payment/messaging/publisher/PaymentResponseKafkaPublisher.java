package com.commerce.payment.service.adapters.payment.messaging.publisher;

import com.commerce.kafka.model.PaymentResponseAvroModel;
import com.commerce.kafka.model.PaymentStatus;
import com.commerce.kafka.producer.KafkaProducer;
import com.commerce.payment.service.common.messaging.kafka.helper.KafkaHelper;
import com.commerce.payment.service.common.outbox.OutboxStatus;
import com.commerce.payment.service.outbox.entity.OrderOutbox;
import com.commerce.payment.service.outbox.entity.OrderOutboxPayload;
import com.commerce.payment.service.payment.port.messaging.output.PaymentResponseMessagePublisher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.UUID;
import java.util.function.BiConsumer;

/**
 * @Author mselvi
 * @Created 08.03.2024
 */

@Component
public class PaymentResponseKafkaPublisher implements PaymentResponseMessagePublisher {

    private static final Logger logger = LoggerFactory.getLogger(PaymentResponseKafkaPublisher.class);
    private final KafkaProducer<String, PaymentResponseAvroModel> kafkaProducer;
    private final KafkaHelper kafkaHelper;

    @Value("${payment-service.payment-response-topic-name}")
    private String paymentResponseTopicName;

    public PaymentResponseKafkaPublisher(KafkaProducer<String, PaymentResponseAvroModel> kafkaProducer, KafkaHelper kafkaHelper) {
        this.kafkaProducer = kafkaProducer;
        this.kafkaHelper = kafkaHelper;
    }

    @Override
    public void publish(OrderOutbox orderOutbox, BiConsumer<OrderOutbox, OutboxStatus> outboxCallback) {
        OrderOutboxPayload payload = kafkaHelper.getPayload(orderOutbox.getPayload(), OrderOutboxPayload.class);
        UUID sagaId = orderOutbox.getSagaId();
        Long orderId = payload.orderId();

        logger.info("Received OrderOutbox for order id: {} and saga id: {}", orderId, sagaId);

        try {
            PaymentResponseAvroModel avroModel = buildAvroModel(sagaId, payload);
            kafkaProducer.send(paymentResponseTopicName, sagaId.toString(), avroModel,
                    kafkaHelper.getKafkaCallback(avroModel, orderOutbox, outboxCallback, orderId));

            logger.info("PaymentResponseAvroModel sent to Kafka for order id: {} and saga id: {}", orderId, sagaId);
        } catch (Exception e) {
            logger.error("Error while sending PaymentResponseAvroModel to kafka with order id: {} and saga id: {} error: {}",
                    orderId, sagaId, e.getMessage());
        }
    }

    private PaymentResponseAvroModel buildAvroModel(UUID sagaId, OrderOutboxPayload payload) {
        return PaymentResponseAvroModel.newBuilder()
                .setSagaId(sagaId.toString())
                .setPaymentId(payload.paymentId())
                .setOrderId(payload.orderId())
                .setCustomerId(payload.customerId())
                .setCost(payload.cost())
                .setPaymentStatus(PaymentStatus.valueOf(payload.paymentStatus().name()))
                .setFailureMessages(payload.failureMessages())
                .build();
    }
}
