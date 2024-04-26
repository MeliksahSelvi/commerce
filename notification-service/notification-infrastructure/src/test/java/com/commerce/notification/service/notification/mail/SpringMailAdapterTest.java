package com.commerce.notification.service.notification.mail;

import com.commerce.notification.service.adapters.notification.mail.SpringMailAdapter;
import com.commerce.notification.service.common.valueobject.NotificationType;
import com.commerce.notification.service.customer.entity.Customer;
import com.commerce.notification.service.notification.usecase.MailContent;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;

/**
 * @Author mselvi
 * @Created 21.03.2024
 */

class SpringMailAdapterTest {

    @InjectMocks
    private SpringMailAdapter adapter;

    @Mock
    private JavaMailSender javaMailSender;

    @Captor
    private ArgumentCaptor<SimpleMailMessage> messageCaptor;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testSendMail() {
        //given
        Customer customer = Customer.builder()
                .id(1L)
                .firstName("John")
                .lastName("Doe")
                .email("john@example.com")
                .build();
        MailContent mailContent = new MailContent(123L, customer, NotificationType.SHIPPING);

        //when
        adapter.sendMail(mailContent);

        //then
        verify(javaMailSender).send(messageCaptor.capture());

        SimpleMailMessage sentMessage = messageCaptor.getValue();
        assertEquals("john@example.com", sentMessage.getTo()[0]);
        assertEquals("SHIPPING", sentMessage.getSubject());

        String expectedBody = String.format("""
                Hi Mr./Mrs. %s %s
                                
                Your order number %d has updated with %s situation.
                """, customer.getFirstName(), customer.getLastName(), mailContent.orderId(), mailContent.notificationType());
        assertEquals(expectedBody, sentMessage.getText());
    }
}
