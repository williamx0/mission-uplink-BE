package com.missionuplink.admindashboard.service.impl;

import com.missionuplink.admindashboard.model.entity.User;
import com.missionuplink.admindashboard.payload.AddUserDto;
import com.missionuplink.admindashboard.payload.AddUserResponse;
import com.missionuplink.admindashboard.repository.UserRepository;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.missionuplink.admindashboard.exception.AuthApiException;
import com.missionuplink.admindashboard.model.entity.AppUser;
import com.missionuplink.admindashboard.model.entity.Device;
import com.missionuplink.admindashboard.payload.LoginDto;
import com.missionuplink.admindashboard.payload.RegisterDto;
import com.missionuplink.admindashboard.payload.UpdateUserInfoDto;
import com.missionuplink.admindashboard.repository.AppUserRepository;
import com.missionuplink.admindashboard.security.JwtTokenProvider;
import com.missionuplink.admindashboard.service.UserService;

@Service
public class UserServiceImpl implements UserService{
// private AuthenticationManager authenticationManager;
    private AppUserRepository appUserRepository;
    private final UserRepository userRepository;
    // private PasswordEncoder passwordEncoder;
    // private JwtTokenProvider jwtTokenProvider;


    @Autowired
    public UserServiceImpl(AppUserRepository appUserRepository, UserRepository userRepository) {
        this.appUserRepository = appUserRepository;
        this.userRepository = userRepository;
    }
    public List<AppUser> getAllWithDate(LocalDate fiveDaysAgo) {
        return appUserRepository.findAllWithDate(fiveDaysAgo);
    }


   
    public String updateInfo(long id, UpdateUserInfoDto updateUserInfoDto){
        Optional<AppUser> appUser = appUserRepository.findById(id);

        if (appUser.isPresent()){
            // if (registerDto.getAverageBandwidth() != null)
            System.out.println(updateUserInfoDto.getAverageBandwidth());
            if (updateUserInfoDto.getAverageBandwidth() != null){
                appUser.get().setAverageBandwidth(updateUserInfoDto.getAverageBandwidth());
            }
            if (updateUserInfoDto.getAverageTimeOnline() != null){
                appUser.get().setAverageTimeOnline(updateUserInfoDto.getAverageTimeOnline());
            }
            if (updateUserInfoDto.getLastFourUrls() != null){
                appUser.get().setLastFourUrls(updateUserInfoDto.getLastFourUrls());
            }
            if (updateUserInfoDto.getTopFourUrls() != null){
                appUser.get().setTopFourUrls(updateUserInfoDto.getTopFourUrls());
            }
            appUserRepository.save(appUser.get());
            return appUser.get().getFirstName();
        }else{
            return "User not found with that id";
        }
    }

    @Override
    public AddUserResponse add(AddUserDto addUserDto) {
        User user = new User();
        user.setFirstName(addUserDto.getFirstName());
        user.setLastName(addUserDto.getLastName());
        user.setType(addUserDto.getType());
        user.setAssignedLab(addUserDto.getAssignedLab());
        user.setValidUntil(addUserDto.getValidUntil());
        user.setStatus(addUserDto.getStatus());
        user.setTemporary(addUserDto.isTemporary());
        user.setCreationDate(LocalDate.now());
        userRepository.save(user);

        AddUserResponse response = new AddUserResponse();
        response.setId(Math.toIntExact(user.getId()));
        response.setStatus(user.getStatus());
        return response;
    }
    @Override
    public List<AppUser> getNewUserBetweenDays(LocalDate desiredDate, LocalDate currentDate) {

        return appUserRepository.findAllBycreationDateBetween(desiredDate,currentDate);
    }
}
