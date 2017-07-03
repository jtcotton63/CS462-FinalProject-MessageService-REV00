package com.josephee.cs462.message.model;

import java.util.ArrayList;
import java.util.List;

public class EmailMessageModel {

    private List<String> toAddresses;
    private String subject;
    private String contents;

    public EmailMessageModel() {
    }

    public EmailMessageModel(List<String> toAddresses, String subject, String contents) {
        this.toAddresses = toAddresses;
        this.subject = subject;
        this.contents = contents;
    }

    public List<String> getToAddresses() {
        if(toAddresses == null)
            toAddresses = new ArrayList<>();

        return toAddresses;
    }

    public boolean addToAddress(String toAddress) {
        if(toAddress == null || toAddress.isEmpty())
            return false;

        return toAddresses.add(toAddress);
    }

    public void setToAddresses(List<String> toAddresses) {
        this.toAddresses = toAddresses;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getContents() {
        return contents;
    }

    public void setContents(String contents) {
        this.contents = contents;
    }
}