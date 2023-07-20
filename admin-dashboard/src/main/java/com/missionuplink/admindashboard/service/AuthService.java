package com.missionuplink.admindashboard.service;

import java.time.LocalDate;
import java.util.List;

import com.missionuplink.admindashboard.model.entity.AppUser;
import com.missionuplink.admindashboard.payload.LoginDto;
import com.missionuplink.admindashboard.payload.RegisterDto;
import com.missionuplink.admindashboard.payload.UpdateUserInfoDto;

public interface AuthService {
    String login(LoginDto loginDto);

    String register(RegisterDto registerDto);

    List<AppUser> getAllWithDate(LocalDate fiveDaysAgo);

    String updateInfo(long id, UpdateUserInfoDto updateUserInfoDto);

}
