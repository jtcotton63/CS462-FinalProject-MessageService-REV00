package com.josephee.cs462.job.controller;

import com.josephee.cs462.job.model.EmailMessageModel;
import com.josephee.cs462.job.service.EmailService;
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
