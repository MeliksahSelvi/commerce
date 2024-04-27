package com.commerce.order.service.adapters.order.messaging.publisher;

import com.commerce.order.service.common.messaging.kafka.model.OrderQueryKafkaModel;
import com.commerce.order.service.common.messaging.kafka.producer.KafkaProducerWithoutCallback;
import com.commerce.order.service.common.valueobject.OrderStatus;
import com.commerce.order.service.order.port.json.JsonPort;
import com.commerce.order.service.order.usecase.OrderQuery;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * @Author mselvi
 * @Created 27.04.2024
 */

class OrderQueryKafkaPublisherTest {

    @InjectMocks
    private OrderQueryKafkaPublisher kafkaPublisher;

    @Mock
    private KafkaProducerWithoutCallback kafkaProducer;

    @Mock
    private JsonPort jsonPort;

    private ObjectMapper objectMapper;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        objectMapper = new ObjectMapper();
    }

    @Test
    void should_publish() throws JsonProcessingException {
        //given
        var orderQuery = buildOrderQuery();
        var kafkaModel = new OrderQueryKafkaModel(orderQuery);
        when(jsonPort.convertDataToJson(kafkaModel)).thenReturn(objectMapper.writeValueAsString(kafkaModel));

        //when
        kafkaPublisher.publish(orderQuery);

        //then
        verify(kafkaProducer).send(any(), any(), any());
    }

    private OrderQuery buildOrderQuery() {
        return new OrderQuery(1L, OrderStatus.PENDING);
    }
}
