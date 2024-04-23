package com.commerce.payment.service.adapters.payment.messaging.listener;

import ch.qos.logback.classic.Level;
import com.commerce.payment.service.adapters.payment.common.LoggerTest;
import com.commerce.payment.service.common.messaging.kafka.model.PaymentRequestKafkaModel;
import com.commerce.payment.service.common.valueobject.OrderPaymentStatus;
import com.commerce.payment.service.payment.port.json.JsonPort;
import com.commerce.payment.service.payment.port.messaging.input.PaymentRequestMessageListener;
import com.commerce.payment.service.payment.usecase.PaymentRequest;
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
class PaymentRequestKafkaListenerTest extends LoggerTest<PaymentRequestKafkaListener> {

    @InjectMocks
    private PaymentRequestKafkaListener kafkaListener;

    @Mock
    private PaymentRequestMessageListener paymentListener;

    @Mock
    private JsonPort jsonPort;

    private ObjectMapper objectMapper;
    private List<String> keys;
    private List<Integer> partitions;
    private List<Long> offsets;

    public PaymentRequestKafkaListenerTest() {
        super(PaymentRequestKafkaListener.class);
    }

    @BeforeEach
    void setUp() {
        this.objectMapper = new ObjectMapper().registerModule(new JavaTimeModule());
        this.keys = buildKeys();
        this.partitions = buildPartitions();
        this.offsets = buildOffsets();
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
        List<String> messagesAsStr = convertKafkaModelListToJsonList(messages);
        when(jsonPort.exractDataFromJson(messagesAsStr.get(0), PaymentRequestKafkaModel.class)).thenReturn(messages.get(0));

        //when
        kafkaListener.receive(messagesAsStr, keys, partitions, offsets);

        //then
        assertTrue(memoryApender.contains(logMessage, Level.INFO));
        verify(paymentListener).completePayment(any(PaymentRequest.class));
    }

    @Test
    void should_receive_when_order_payment_status_cancelled() {
        //given
        var messages = buildMessages(OrderPaymentStatus.CANCELLED);
        var logMessage = buildLogMessage(messages, keys, partitions, offsets);
        List<String> messagesAsStr = convertKafkaModelListToJsonList(messages);
        when(jsonPort.exractDataFromJson(messagesAsStr.get(0), PaymentRequestKafkaModel.class)).thenReturn(messages.get(0));

        //when
        kafkaListener.receive(messagesAsStr, keys, partitions, offsets);

        //then
        assertTrue(memoryApender.contains(logMessage, Level.INFO));
        verify(paymentListener).cancelPayment(any(PaymentRequest.class));
    }

    private List<PaymentRequestKafkaModel> buildMessages(OrderPaymentStatus orderPaymentStatus) {
        var kafkaModel = buildKafkaModel(orderPaymentStatus);
        List<PaymentRequestKafkaModel> messages = new ArrayList<>();
        messages.add(kafkaModel);
        return messages;
    }

    private PaymentRequestKafkaModel buildKafkaModel(OrderPaymentStatus orderPaymentStatus) {
        return new PaymentRequestKafkaModel(UUID.randomUUID().toString(), 1L, 1L, BigDecimal.ONE, orderPaymentStatus);
    }

    private String buildLogMessage(List<PaymentRequestKafkaModel> messages, List<String> keys, List<Integer> partitions, List<Long> offsets) {
        return String.format("%d number of messages received with keys:[%s], partitions:[%d] and offsets:[%d]"
                , messages.size(), keys.get(0), partitions.get(0), offsets.get(0));
    }

    private List<String> convertKafkaModelListToJsonList(List<PaymentRequestKafkaModel> kafkaModelList) {
        return kafkaModelList.stream().map(kafkaModel -> {
            try {
                return objectMapper.writeValueAsString(kafkaModel);
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }
        }).toList();
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


}
