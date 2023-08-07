package com.missionuplink.admindashboard.service;

import com.missionuplink.admindashboard.payload.AddUserDto;
import com.missionuplink.admindashboard.payload.AddUserResponse;
import java.time.LocalDate;
import java.util.List;

import com.missionuplink.admindashboard.model.entity.AppUser;
import com.missionuplink.admindashboard.payload.UpdateUserInfoDto;

public interface UserService {
    
    List<AppUser> getAllWithDate(LocalDate fiveDaysAgo);

    String updateInfo(long id, UpdateUserInfoDto updateUserInfoDto);

    AddUserResponse add(AddUserDto addUserDto);
}
