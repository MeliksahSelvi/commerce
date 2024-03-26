package com.commerce.order.service.adapters.order.messaging.listener;

import ch.qos.logback.classic.Level;
import com.commerce.kafka.model.PaymentResponseAvroModel;
import com.commerce.kafka.model.PaymentStatus;
import com.commerce.order.service.adapters.order.common.LoggerTest;
import com.commerce.order.service.adapters.order.messaging.listener.common.ListenerCommonData;
import com.commerce.order.service.common.saga.SagaStep;
import com.commerce.order.service.order.usecase.PaymentResponse;
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
class PaymentResponseKafkaListenerTest extends LoggerTest<PaymentResponseKafkaListener> {

    @InjectMocks
    private PaymentResponseKafkaListener kafkaListener;

    @Mock
    private SagaStep<PaymentResponse> paymentResponseSagaStep;

    private ListenerCommonData listenerCommonData;
    private List<String> keys;
    private List<Integer> partitions;
    private List<Long> offsets;


    public PaymentResponseKafkaListenerTest() {
        super(PaymentResponseKafkaListener.class);
    }

    @BeforeEach
    void setUp() {
        listenerCommonData = new ListenerCommonData();
        keys = listenerCommonData.buildKeys();
        partitions = listenerCommonData.buildPartitions();
        offsets = listenerCommonData.buildOffsets();
    }

    @AfterEach
    @Override
    protected void cleanUp() {
        cleanUpActions();
    }

    @Test
    void should_receive_when_payment_status_completed() {
        //given
        var messages = buildMessages(PaymentStatus.COMPLETED);
        var logMessage = listenerCommonData.buildLogMessage(messages, keys, partitions, offsets);

        //when
        kafkaListener.receive(messages, keys, partitions, offsets);

        //then
        assertTrue(memoryApender.contains(logMessage, Level.INFO));
        verify(paymentResponseSagaStep).process(any(PaymentResponse.class));
    }

    @Test
    void should_receive_when_payment_status_cancelled() {
        //given
        var messages = buildMessages(PaymentStatus.CANCELLED);
        var logMessage = listenerCommonData.buildLogMessage(messages, keys, partitions, offsets);

        //when
        kafkaListener.receive(messages, keys, partitions, offsets);

        //then
        assertTrue(memoryApender.contains(logMessage, Level.INFO));
        verify(paymentResponseSagaStep).rollback(any(PaymentResponse.class));
    }

    @Test
    void should_receive_when_payment_status_failed() {
        //given
        var messages = buildMessages(PaymentStatus.FAILED);
        var logMessage = listenerCommonData.buildLogMessage(messages, keys, partitions, offsets);

        //when
        kafkaListener.receive(messages, keys, partitions, offsets);

        //then
        assertTrue(memoryApender.contains(logMessage, Level.INFO));
        verify(paymentResponseSagaStep).rollback(any(PaymentResponse.class));
    }

    private List<PaymentResponseAvroModel> buildMessages(PaymentStatus paymentStatus) {
        var avroModel = buildAvroModel(paymentStatus);
        List<PaymentResponseAvroModel> messages = new ArrayList<>();
        messages.add(avroModel);
        return messages;
    }

    private PaymentResponseAvroModel buildAvroModel(PaymentStatus paymentStatus) {
        return PaymentResponseAvroModel.newBuilder()
                .setSagaId(UUID.randomUUID().toString())
                .setPaymentId(1L)
                .setCost(BigDecimal.ONE)
                .setPaymentStatus(paymentStatus)
                .setOrderId(1L)
                .setCustomerId(1L)
                .setFailureMessages(new ArrayList<>())
                .build();
    }
}
