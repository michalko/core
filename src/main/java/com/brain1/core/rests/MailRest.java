package com.brain1.core.rests;

import com.brain1.core.Integration.IntegrationGateway;
import com.brain1.core.transport.MailRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/mail")
@CrossOrigin(origins = { "http://localhost:3000", "https://brainmatter.xyz" })
public class MailRest {
    @Autowired
    private IntegrationGateway integrationGateway;

    @PostMapping
    @ResponseStatus(code = HttpStatus.OK)
    public void sentLinksMail(@RequestBody MailRequest mailRequest) {
        integrationGateway.sendMessage(mailRequest);
    }
}

