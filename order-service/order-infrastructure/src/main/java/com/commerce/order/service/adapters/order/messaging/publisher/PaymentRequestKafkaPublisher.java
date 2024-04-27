package com.commerce.order.service.adapters.order.messaging.publisher;

import com.commerce.order.service.common.messaging.kafka.helper.KafkaHelper;
import com.commerce.order.service.common.messaging.kafka.model.PaymentRequestKafkaModel;
import com.commerce.order.service.common.messaging.kafka.producer.KafkaProducer;
import com.commerce.order.service.common.outbox.OutboxStatus;
import com.commerce.order.service.order.port.json.JsonPort;
import com.commerce.order.service.order.port.messaging.output.PaymentRequestMessagePublisher;
import com.commerce.order.service.outbox.model.PaymentOutbox;
import com.commerce.order.service.outbox.model.PaymentOutboxPayload;
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
    private final KafkaProducer kafkaProducer;
    private final KafkaHelper kafkaHelper;
    private final JsonPort jsonPort;

    @Value("${order-service.payment-request-topic-name}")
    private String paymentRequestTopicName;

    public PaymentRequestKafkaPublisher(KafkaProducer kafkaProducer, KafkaHelper kafkaHelper, JsonPort jsonPort) {
        this.kafkaProducer = kafkaProducer;
        this.kafkaHelper = kafkaHelper;
        this.jsonPort = jsonPort;
    }

    @Override
    public void publish(PaymentOutbox paymentOutbox, BiConsumer<PaymentOutbox, OutboxStatus> outboxCallback) {
        PaymentOutboxPayload paymentOutboxPayload = kafkaHelper.getPayload(paymentOutbox.getPayload(), PaymentOutboxPayload.class);
        UUID sagaId = paymentOutboxPayload.sagaId();
        Long orderId = paymentOutboxPayload.orderId();

        logger.info("Received PaymentOutbox for order id: {} and saga id: {}", orderId, sagaId);

        try {
            PaymentRequestKafkaModel kafkaModel = new PaymentRequestKafkaModel(paymentOutboxPayload);
            kafkaProducer.send(paymentRequestTopicName, sagaId.toString(), jsonPort.convertDataToJson(kafkaModel),
                    kafkaHelper.getKafkaCallback(kafkaModel, paymentOutbox, outboxCallback, orderId));
            logger.info("PaymentRequestKafkaModel sent to Kafka for order id: {} and saga id: {}", orderId, sagaId);
        } catch (Exception e) {
            logger.error("Error while sending PaymentRequestKafkaModel to Kafka with order id: {} and saga id: {} error: {}", orderId, sagaId, e.getMessage());
        }
    }
}
