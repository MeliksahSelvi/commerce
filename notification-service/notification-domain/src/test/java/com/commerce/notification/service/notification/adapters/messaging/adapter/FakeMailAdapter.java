package com.commerce.notification.service.notification.adapters.messaging.adapter;

import com.commerce.notification.service.notification.port.mail.MailPort;
import com.commerce.notification.service.notification.usecase.MailContent;

/**
 * @Author mselvi
 * @Created 21.03.2024
 */

public class FakeMailAdapter implements MailPort {

    @Override
    public void sendMail(MailContent mailContent) {

    }
}
