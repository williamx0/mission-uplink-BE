package com.missionuplink.admindashboard.service;

import com.missionuplink.admindashboard.model.entity.AppUser;
import java.util.Optional;

public interface PasswordResetTokenService {
    void createPasswordResetTokenForUser(String token, AppUser appUser);

    String validatePasswordResetToken(String token);

    Optional<AppUser> findAppUserByPasswordResetToken(String token);
}
