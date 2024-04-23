package com.commerce.order.service.adapters.order.messaging.listener;

import ch.qos.logback.classic.Level;
import com.commerce.order.service.adapters.order.common.LoggerTest;
import com.commerce.order.service.adapters.order.messaging.listener.common.ListenerCommonData;
import com.commerce.order.service.common.messaging.kafka.model.PaymentResponseKafkaModel;
import com.commerce.order.service.common.saga.SagaStep;
import com.commerce.order.service.common.valueobject.PaymentStatus;
import com.commerce.order.service.order.port.json.JsonPort;
import com.commerce.order.service.order.usecase.PaymentResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
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
import static org.mockito.Mockito.when;

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

    @Mock
    private JsonPort jsonPort;

    private ObjectMapper objectMapper;
    private ListenerCommonData listenerCommonData;
    private List<String> keys;
    private List<Integer> partitions;
    private List<Long> offsets;


    public PaymentResponseKafkaListenerTest() {
        super(PaymentResponseKafkaListener.class);
    }

    @BeforeEach
    void setUp() {
        this.objectMapper = new ObjectMapper().registerModule(new JavaTimeModule());
        this.listenerCommonData = new ListenerCommonData();
        this.keys = listenerCommonData.buildKeys();
        this.partitions = listenerCommonData.buildPartitions();
        this.offsets = listenerCommonData.buildOffsets();
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
        List<String> messagesAsStr = convertKafkaModelListToJsonList(messages);
        when(jsonPort.exractDataFromJson(messagesAsStr.get(0), PaymentResponseKafkaModel.class)).thenReturn(messages.get(0));

        //when
        kafkaListener.receive(messagesAsStr, keys, partitions, offsets);
        //then
        assertTrue(memoryApender.contains(logMessage, Level.INFO));
        verify(paymentResponseSagaStep).process(any(PaymentResponse.class));
    }

    @Test
    void should_receive_when_payment_status_cancelled() {
        //given
        var messages = buildMessages(PaymentStatus.CANCELLED);
        var logMessage = listenerCommonData.buildLogMessage(messages, keys, partitions, offsets);
        List<String> messagesAsStr = convertKafkaModelListToJsonList(messages);
        when(jsonPort.exractDataFromJson(messagesAsStr.get(0), PaymentResponseKafkaModel.class)).thenReturn(messages.get(0));

        //when
        kafkaListener.receive(messagesAsStr, keys, partitions, offsets);

        //then
        assertTrue(memoryApender.contains(logMessage, Level.INFO));
        verify(paymentResponseSagaStep).rollback(any(PaymentResponse.class));
    }

    @Test
    void should_receive_when_payment_status_failed() {
        //given
        var messages = buildMessages(PaymentStatus.FAILED);
        var logMessage = listenerCommonData.buildLogMessage(messages, keys, partitions, offsets);
        List<String> messagesAsStr = convertKafkaModelListToJsonList(messages);
        when(jsonPort.exractDataFromJson(messagesAsStr.get(0), PaymentResponseKafkaModel.class)).thenReturn(messages.get(0));

        //when
        kafkaListener.receive(messagesAsStr, keys, partitions, offsets);

        //then
        assertTrue(memoryApender.contains(logMessage, Level.INFO));
        verify(paymentResponseSagaStep).rollback(any(PaymentResponse.class));
    }

    private List<PaymentResponseKafkaModel> buildMessages(PaymentStatus paymentStatus) {
        var kafkaModel = buildKafkaModel(paymentStatus);
        List<PaymentResponseKafkaModel> messages = new ArrayList<>();
        messages.add(kafkaModel);
        return messages;
    }

    private PaymentResponseKafkaModel buildKafkaModel(PaymentStatus paymentStatus) {
        return new PaymentResponseKafkaModel(UUID.randomUUID().toString(), 1L, 1L, 1L, BigDecimal.ONE, paymentStatus, new ArrayList<>());
    }

    private List<String> convertKafkaModelListToJsonList(List<PaymentResponseKafkaModel> kafkaModelList) {
        return kafkaModelList.stream().map(kafkaModel -> {
            try {
                return objectMapper.writeValueAsString(kafkaModel);
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }
        }).toList();
    }
}
