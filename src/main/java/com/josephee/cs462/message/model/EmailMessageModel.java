package com.josephee.cs462.message.model;

import java.util.HashSet;

public class EmailMessageModel {

    private HashSet<String> toAddresses;
    private String subject;
    private String contents;

    public EmailMessageModel() {
    }

    public EmailMessageModel(HashSet<String> toAddresses, String subject, String contents) {
        this.toAddresses = toAddresses;
        this.subject = subject;
        this.contents = contents;
    }

    public HashSet<String> getToAddresses() {
        if(toAddresses == null)
            toAddresses = new HashSet<>();

        return toAddresses;
    }

    public boolean addToAddress(String toAddress) {
        if(toAddress == null || toAddress.isEmpty())
            return false;

        return toAddresses.add(toAddress);
    }

    public void setToAddresses(HashSet<String> toAddresses) {
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