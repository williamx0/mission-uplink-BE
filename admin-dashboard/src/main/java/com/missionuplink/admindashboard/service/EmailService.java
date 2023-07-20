package com.missionuplink.admindashboard.service;

import com.missionuplink.admindashboard.payload.EmailDto;

public interface EmailService {
    void sendTestEmail(EmailDto emailDto);

    void sendForgetPasswordEmail(EmailDto emailDto);
}
