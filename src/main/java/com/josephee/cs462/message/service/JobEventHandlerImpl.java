package com.josephee.cs462.message.service;

import com.josephee.cs462.common.client.UserClient;
import com.josephee.cs462.common.handler.JobEventHandler;
import com.josephee.cs462.common.model.event.CreateEvent;
import com.josephee.cs462.common.model.event.UpdatedEvent;
import com.josephee.cs462.common.model.job.JobModel;
import com.josephee.cs462.common.model.user.Role;
import com.josephee.cs462.common.model.user.UserModel;
import com.josephee.cs462.message.model.EmailMessageModel;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

@Service
public class JobEventHandlerImpl extends JobEventHandler {

    private EmailService emailService;
    private UserClient userClient;

    @Autowired
    protected JobEventHandlerImpl(EmailService emailService, UserClient userClient) {
        super(LoggerFactory.getLogger(JobEventHandlerImpl.class));
        this.emailService = emailService;
        this.userClient = userClient;
    }

    private HashSet<String> getEmails(List<UserModel> users) {
        HashSet<String> emails = new HashSet<>(users.size());
        for(UserModel user: users)
            emails.add(user.getEmail());
        return emails;
    }

    @Override
    public void jobCreatedChild(CreateEvent<JobModel> event) {
        List<UserModel> allHelpers = userClient.getUsers(Optional.of(Role.HELPER));
        HashSet<String> emails = getEmails(allHelpers);
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
