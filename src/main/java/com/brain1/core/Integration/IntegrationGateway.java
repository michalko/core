package com.brain1.core.Integration;

import com.brain1.core.transport.MailRequest;

import org.springframework.integration.annotation.Gateway;
import org.springframework.integration.annotation.MessagingGateway;

@MessagingGateway
public interface IntegrationGateway {
    @Gateway(requestChannel = "integration.request.channel")
    public String sendMessage(MailRequest mailRequest);
}