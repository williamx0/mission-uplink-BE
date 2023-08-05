package com.missionuplink.admindashboard.controller;

import com.missionuplink.admindashboard.model.entity.AppUser;
import com.missionuplink.admindashboard.payload.EmailDto;
import com.missionuplink.admindashboard.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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

    @PostMapping("/test")
    public ResponseEntity<String> sendTestEmail(@RequestBody EmailDto request) {
        emailService.sendTestEmail(request);
        return new ResponseEntity<>("Email sent successfully", HttpStatus.OK);
    }

    @PostMapping("/forget-password")
    public ResponseEntity<String> sendForgetPasswordEmail(@RequestBody EmailDto request) {
        emailService.sendForgetPasswordEmail(request);
        return new ResponseEntity<>("Email sent successfully to " + request.getTo(), HttpStatus.OK);
    }
}
