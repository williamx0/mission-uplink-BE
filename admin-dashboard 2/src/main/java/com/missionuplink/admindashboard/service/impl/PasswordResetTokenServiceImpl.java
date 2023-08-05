package com.missionuplink.admindashboard.service.impl;

import com.missionuplink.admindashboard.model.entity.AppUser;
import com.missionuplink.admindashboard.model.entity.PasswordResetToken;
import com.missionuplink.admindashboard.repository.PasswordResetTokenRepository;
import com.missionuplink.admindashboard.service.PasswordResetTokenService;
import java.util.Calendar;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PasswordResetTokenServiceImpl implements PasswordResetTokenService {

    private final PasswordResetTokenRepository passwordResetTokenRepository;

    @Override
    public void createPasswordResetTokenForUser(String token, AppUser appUser) {
        PasswordResetToken passwordResetToken = new PasswordResetToken(token, appUser);
        passwordResetTokenRepository.save(passwordResetToken);
    }

    @Override
    public String validatePasswordResetToken(String token) {
        PasswordResetToken passwordResetToken = passwordResetTokenRepository.findByToken(token);
        if (passwordResetToken == null) return "Invalid password reset token";

        AppUser appUser = passwordResetToken.getAppUser();
        Calendar calendar = Calendar.getInstance();

        if (passwordResetToken.getExpirationTime().getTime() - calendar.getTime().getTime() <= 0)
            return "Password reset link has already expired, resend link";

        return "valid";
    }

    @Override
    public Optional<AppUser> findAppUserByPasswordResetToken(String token) {
        return Optional.ofNullable(passwordResetTokenRepository.findByToken(token).getAppUser());
    }
}
