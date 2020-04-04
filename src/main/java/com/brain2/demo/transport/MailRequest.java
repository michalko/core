package com.brain2.demo.transport;

import java.util.List;

import javax.validation.constraints.Email;

public class MailRequest {
    @Email
    public String email;
    public List<String> listOfLinks;

    public MailRequest(@Email String email, List<String> listOfLinks) {
        this.email = email;
        this.listOfLinks = listOfLinks;
    }
}