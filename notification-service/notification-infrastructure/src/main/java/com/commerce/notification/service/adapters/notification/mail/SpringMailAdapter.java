package com.commerce.notification.service.adapters.notification.mail;

import com.commerce.notification.service.customer.model.Customer;
import com.commerce.notification.service.notification.port.mail.MailPort;
import com.commerce.notification.service.notification.usecase.MailContent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

/**
 * @Author mselvi
 * @Created 08.03.2024
 */

@Service
public class SpringMailAdapter implements MailPort {

    private final Logger logger = LoggerFactory.getLogger(SpringMailAdapter.class);

    private final JavaMailSender javaMailSender;

    @Value("${spring.mail.username}")
    private String whoSendEmail;

    public SpringMailAdapter(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }

    @Override
    public void sendMail(MailContent mailContent) {
        Customer customer = mailContent.customer();
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setFrom(whoSendEmail);
        mailMessage.setSubject(mailContent.notificationType().toString());
        mailMessage.setText(generateMailBody(mailContent));
        mailMessage.setTo(customer.getEmail());

        String customerFullname = customer.getFirstName() + customer.getLastName();
        try {
            javaMailSender.send(mailMessage);
            logger.info("Mail sent to customer -> {}", customerFullname);
        } catch (Exception e) {
            logger.error("Mail could not sent to customer -> {}", customerFullname);
        }
    }

    private String generateMailBody(MailContent mailContent) {
        Customer customer = mailContent.customer();
        return String.format("""
                Hi Mr./Mrs. %s %s
                                
                Your order number %d has updated with %s situation.
                """, customer.getFirstName(), customer.getLastName(), mailContent.orderId(), mailContent.notificationType());
    }
}
