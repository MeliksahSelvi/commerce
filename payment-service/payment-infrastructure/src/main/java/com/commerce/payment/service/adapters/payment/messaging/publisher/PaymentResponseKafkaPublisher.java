package com.commerce.payment.service.adapters.payment.messaging.publisher;

import com.commerce.payment.service.common.messaging.kafka.helper.KafkaHelper;
import com.commerce.payment.service.common.messaging.kafka.model.PaymentResponseKafkaModel;
import com.commerce.payment.service.common.messaging.kafka.producer.KafkaProducer;
import com.commerce.payment.service.common.outbox.OutboxStatus;
import com.commerce.payment.service.outbox.entity.OrderOutbox;
import com.commerce.payment.service.outbox.entity.OrderOutboxPayload;
import com.commerce.payment.service.payment.port.json.JsonPort;
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
    private final KafkaProducer kafkaProducer;
    private final KafkaHelper kafkaHelper;
    private final JsonPort jsonPort;

    @Value("${payment-service.payment-response-topic-name}")
    private String paymentResponseTopicName;

    public PaymentResponseKafkaPublisher(KafkaProducer kafkaProducer, KafkaHelper kafkaHelper, JsonPort jsonPort) {
        this.kafkaProducer = kafkaProducer;
        this.kafkaHelper = kafkaHelper;
        this.jsonPort = jsonPort;
    }

    @Override
    public void publish(OrderOutbox orderOutbox, BiConsumer<OrderOutbox, OutboxStatus> outboxCallback) {
        OrderOutboxPayload payload = kafkaHelper.getPayload(orderOutbox.getPayload(), OrderOutboxPayload.class);
        UUID sagaId = orderOutbox.getSagaId();
        Long orderId = payload.orderId();

        logger.info("Received OrderOutbox for order id: {} and saga id: {}", orderId, sagaId);

        try {
            PaymentResponseKafkaModel kafkaModel = new PaymentResponseKafkaModel(sagaId, payload);
            kafkaProducer.send(paymentResponseTopicName, sagaId.toString(), jsonPort.convertDataToJson(kafkaModel),
                    kafkaHelper.getKafkaCallback(kafkaModel, orderOutbox, outboxCallback, orderId));

            logger.info("PaymentResponseAvroModel sent to Kafka for order id: {} and saga id: {}", orderId, sagaId);
        } catch (Exception e) {
            logger.error("Error while sending PaymentResponseAvroModel to kafka with order id: {} and saga id: {} error: {}",
                    orderId, sagaId, e.getMessage());
        }
    }
}
