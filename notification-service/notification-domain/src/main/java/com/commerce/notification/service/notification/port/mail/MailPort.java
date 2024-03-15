package com.commerce.notification.service.notification.port.mail;

import com.commerce.notification.service.notification.usecase.MailContent;

/**
 * @Author mselvi
 * @Created 08.03.2024
 */

public interface MailPort {

    void sendMail(MailContent mailContent);
}
