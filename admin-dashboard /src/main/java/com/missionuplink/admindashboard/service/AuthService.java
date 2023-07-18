package com.missionuplink.admindashboard.service;

import com.missionuplink.admindashboard.payload.LoginDto;
import com.missionuplink.admindashboard.payload.RegisterDto;

public interface AuthService {
    String login(LoginDto loginDto);

    String register(RegisterDto registerDto);

}
