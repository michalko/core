package com.brain2.demo.Integration;

import com.brain2.demo.transport.MailRequest;

import org.springframework.integration.annotation.Gateway;
import org.springframework.integration.annotation.MessagingGateway;

@MessagingGateway
public interface IntegrationGateway {
    @Gateway(requestChannel = "integration.request.channel")
    public String sendMessage(MailRequest mailRequest);
}