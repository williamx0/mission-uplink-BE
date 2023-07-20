package com.missionuplink.admindashboard.payload;

import lombok.Data;

@Data
public class PasswordResetDto {
    private String email;
    private String newPassword;
    private String confirmPassword;
}
