package com.josephee.cs462.message.service;

import com.josephee.cs462.message.model.EmailMessageModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service("emailService")
public class EmailService {

    private static final Logger LOG = LoggerFactory.getLogger(EmailService.class);

    private static final String NO_REPLY = "jtcotton63+noreply@gmail.com";

    @Autowired
    private JavaMailSender mailSender;

    public void create(EmailMessageModel incoming) throws MessagingException {
        if(incoming.getContents() == null || incoming.getContents().isEmpty())
            return;

        List<InternetAddress> internetAddressList = getInternetAddressList(incoming.getToAddresses());
        for(InternetAddress internetAddress: internetAddressList)
            sendSingle(internetAddress, incoming);
    }

    private List<InternetAddress> getInternetAddressList(List<String> toAddresses) throws AddressException {
        List<InternetAddress> internetAddresses = new ArrayList<>(toAddresses.size());
        for(String address: toAddresses) {
            InternetAddress[] internetAddress = InternetAddress.parse(address);
            internetAddresses.add(internetAddress[0]);
        }

        return internetAddresses;
    }

    private void sendSingle(InternetAddress recipient, EmailMessageModel incoming) throws MessagingException {
        try {

            MimeMessage mimeMessage = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
            helper.setTo(recipient);
            helper.setFrom(NO_REPLY);
            helper.setReplyTo(NO_REPLY);
            helper.setSentDate(new Date());
            helper.setSubject(incoming.getSubject());
            helper.setText(incoming.getContents(), false);
            mailSender.send(mimeMessage);

        } catch (MailException | MessagingException e) {
            LOG.error("Failed to send email to " + recipient, e);
            throw e;
        }
    }
}
