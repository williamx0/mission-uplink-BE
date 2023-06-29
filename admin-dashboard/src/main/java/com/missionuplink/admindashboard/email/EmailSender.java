package com.missionuplink.admindashboard.email;

public interface EmailSender {
    void send(String to, String subject, String body);
}
