package com.commerce.payment.service.adapters.payment.messaging.listener;

import ch.qos.logback.classic.Level;
import com.commerce.kafka.model.OrderPaymentStatus;
import com.commerce.kafka.model.PaymentRequestAvroModel;
import com.commerce.payment.service.adapters.payment.common.LoggerTest;
import com.commerce.payment.service.payment.port.messaging.input.PaymentRequestMessageListener;
import com.commerce.payment.service.payment.usecase.PaymentRequest;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;

/**
 * @Author mselvi
 * @Created 21.03.2024
 */

@ExtendWith(MockitoExtension.class)
class PaymentRequestKafkaListenerTest extends LoggerTest<PaymentRequestKafkaListener> {

    @InjectMocks
    private PaymentRequestKafkaListener kafkaListener;

    @Mock
    private PaymentRequestMessageListener paymentListener;

    private List<String> keys;
    private List<Integer> partitions;
    private List<Long> offsets;

    public PaymentRequestKafkaListenerTest() {
        super(PaymentRequestKafkaListener.class);
    }

    @BeforeEach
    void setUp() {
        keys = buildKeys();
        partitions = buildPartitions();
        offsets = buildOffsets();
    }

    @AfterEach
    @Override
    protected void cleanUp() {
        cleanUpActions();
    }

    @Test
    void should_receive_when_order_payment_status_pending() {
        //given
        var messages = buildMessages(OrderPaymentStatus.PENDING);
        var logMessage = buildLogMessage(messages, keys, partitions, offsets);

        //when
        kafkaListener.receive(messages, keys, partitions, offsets);

        //then
        assertTrue(memoryApender.contains(logMessage, Level.INFO));
        verify(paymentListener).completePayment(any(PaymentRequest.class));
    }

    @Test
    void should_receive_when_order_payment_status_cancelled() {
        //given
        var messages = buildMessages(OrderPaymentStatus.CANCELLED);
        var logMessage = buildLogMessage(messages, keys, partitions, offsets);

        //when
        kafkaListener.receive(messages, keys, partitions, offsets);

        //then
        assertTrue(memoryApender.contains(logMessage, Level.INFO));
        verify(paymentListener).cancelPayment(any(PaymentRequest.class));
    }

    private List<PaymentRequestAvroModel> buildMessages(OrderPaymentStatus orderPaymentStatus) {
        var avroModel = buildAvroModel(orderPaymentStatus);
        List<PaymentRequestAvroModel> messages = new ArrayList<>();
        messages.add(avroModel);
        return messages;
    }

    private PaymentRequestAvroModel buildAvroModel(OrderPaymentStatus orderPaymentStatus) {
        return PaymentRequestAvroModel.newBuilder()
                .setOrderPaymentStatus(orderPaymentStatus)
                .setSagaId(UUID.randomUUID().toString())
                .setCost(BigDecimal.ONE)
                .setOrderId(1L)
                .setCustomerId(1L)
                .build();
    }

    private List<String> buildKeys() {
        List<String> keys = new ArrayList<>();
        keys.add("key1");
        return keys;
    }

    private List<Integer> buildPartitions() {
        List<Integer> partitions = new ArrayList<>();
        partitions.add(0);
        return partitions;
    }

    private List<Long> buildOffsets() {
        List<Long> offsets = new ArrayList<>();
        offsets.add(0L);
        return offsets;
    }

    private String buildLogMessage(List messages, List<String> keys, List<Integer> partitions, List<Long> offsets) {
        return String.format("%d number of messages received with keys:[%s], partitions:[%d] and offsets:[%d]"
                , messages.size(), keys.get(0), partitions.get(0), offsets.get(0));
    }
}
