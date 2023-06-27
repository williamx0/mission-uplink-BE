package com.missionuplink.admindashboard.email;

import com.missionuplink.admindashboard.appuser.AppUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping(path = "/send-email")
@AllArgsConstructor
public class EmailController {
    @Autowired
    private EmailService emailService;

    record TestEmailRequest(
            String to,
            String subject,
            String body
    ) {}

    @PostMapping("/test")
    public void sendTestEmail(@RequestBody TestEmailRequest request) {
        emailService.send(request.to, request.subject, request.body);
    }

    record ForgetPasswordEmailRequest(
            String body,
            AppUser appUser
    ) {}

    @PostMapping("/forget-password")
    public void sendForgetPasswordEmail(@RequestBody ForgetPasswordEmailRequest request) {

    }
}
