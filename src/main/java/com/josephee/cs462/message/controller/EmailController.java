package com.josephee.cs462.message.controller;

import com.josephee.cs462.message.model.EmailMessageModel;
import com.josephee.cs462.message.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.mail.MessagingException;

@RestController
@RequestMapping("/email")
public class EmailController {

    @Autowired
    private EmailService emailService;

    @RequestMapping(value = "", method = RequestMethod.POST)
    public void sendMail(@RequestBody EmailMessageModel model) throws MessagingException {
        emailService.create(model);
    }
}
