package com.brain1.core.Integration;

import com.brain1.core.config.MailConfig;
import com.brain1.core.transport.MailRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.integration.mail.MailSendingMessageHandler;
import org.springframework.mail.MailMessage;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;

@Component
public class IntegrationService {

    @Autowired
    MailConfig mailConfig;

    @ServiceActivator(inputChannel = "integration.request.channel", outputChannel = "my.mailSender")
    public MailMessage logMail(Message<MailRequest> message) {
        MailMessage mailMsg = new SimpleMailMessage();
        mailMsg.setFrom("michal.lewandowski@gmail.com");
        mailMsg.setTo(message.getPayload().email);
        mailMsg.setSubject("Study materials from Brainmatter");

        StringBuilder textMessage = new StringBuilder("Study materials: ").append(message.getPayload().listOfLinks)
                .append(System.getProperty("line.separator").repeat(2)).append("Have fun, bm");
        mailMsg.setText(textMessage.toString());

        System.out.println(mailMsg.toString());

        return mailMsg;
    }

    @Bean
    @ServiceActivator(inputChannel = "my.mailSender")
    public MailSendingMessageHandler mailSendingMessageHandler() {
        MailSendingMessageHandler mailSendingMessageHandler = new MailSendingMessageHandler(
                mailConfig.javaMailSender());
        return mailSendingMessageHandler;
    }

}