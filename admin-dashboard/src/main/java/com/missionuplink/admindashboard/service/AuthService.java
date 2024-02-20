package com.missionuplink.admindashboard.service;

import com.missionuplink.admindashboard.model.entity.AppUser;
import java.time.LocalDate;
import java.util.List;

///import com.missionuplink.admindashboard.model.entity.AppUser;
import com.missionuplink.admindashboard.payload.DeviceDto;
import com.missionuplink.admindashboard.payload.DeviceLoginDto;
import com.missionuplink.admindashboard.payload.LoginDto;
import com.missionuplink.admindashboard.payload.RegisterDto;
import com.missionuplink.admindashboard.model.entity.Device;

import java.util.Optional;
import com.missionuplink.admindashboard.payload.UpdateUserInfoDto;

public interface AuthService {
    String[] login(LoginDto loginDto);
    
    String deviceLogin(DeviceLoginDto loginDto);

    String register(RegisterDto registerDto);

    Device registerDevice(DeviceDto device);

    List<AppUser> getAllWithDate(LocalDate fiveDaysAgo);

    String updateInfo(long id, UpdateUserInfoDto updateUserInfoDto);

    Optional<AppUser> findUserByEmail(String email);

    void createPasswordResetTokenForUser(String token, AppUser appUser);

    String validatePasswordResetToken(String token);

    AppUser findUserByPasswordResetToken(String token);

    void resetUserPassword(AppUser appUser, String newPassword);
}
