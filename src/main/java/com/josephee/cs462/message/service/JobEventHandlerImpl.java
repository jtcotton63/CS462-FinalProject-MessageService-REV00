package com.josephee.cs462.message.service;

import com.josephee.cs462.common.client.HelperClient;
import com.josephee.cs462.common.handler.JobEventHandler;
import com.josephee.cs462.common.model.event.CreateEvent;
import com.josephee.cs462.common.model.event.UpdatedEvent;
import com.josephee.cs462.common.model.helper.HelperModel;
import com.josephee.cs462.common.model.job.JobModel;
import com.josephee.cs462.message.model.EmailMessageModel;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import java.util.HashSet;
import java.util.List;

@Service
public class JobEventHandlerImpl extends JobEventHandler {

    private EmailService emailService;
    private HelperClient helperClient;

    @Autowired
    protected JobEventHandlerImpl(EmailService emailService, HelperClient helperClient) {
        super(LoggerFactory.getLogger(JobEventHandlerImpl.class));
        this.emailService = emailService;
        this.helperClient = helperClient;
    }

    private HashSet<String> getAllEmails(List<HelperModel> helpers) {
        HashSet<String> emails = new HashSet<>(helpers.size());
        for(HelperModel helper: helpers)
            emails.add(helper.getEmail());
        return emails;
    }

    @Override
    public void jobCreatedChild(CreateEvent<JobModel> event) {
        List<HelperModel> allHelpers = helperClient.getAllHelpers();
        HashSet<String> emails = getAllEmails(allHelpers);
        EmailMessageModel email = new EmailMessageModel();
        email.setToAddresses(emails);
        email.setSubject("New Furniture Job Available");
        email.setContents("Uncle David needs help with a new furniture job.\n\nDetails: " + event.getCreatedAt().toString() + "\n\nA link should go here");
        try {
            emailService.sendEmail(email);
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void jobUpdatedChild(UpdatedEvent<JobModel> event) {
        // TODO: Implement me
    }
}
