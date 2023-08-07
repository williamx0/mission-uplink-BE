package com.missionuplink.admindashboard.controller;

import com.missionuplink.admindashboard.model.enums.AppUserStatus;
import com.missionuplink.admindashboard.payload.JWTAuthResponse;
import com.missionuplink.admindashboard.payload.LoginDto;
import com.missionuplink.admindashboard.payload.RegisterDto;
import com.missionuplink.admindashboard.payload.UpdateUserInfoDto;
import com.missionuplink.admindashboard.service.UserService;

import java.time.LocalDate;
import java.util.List;

import org.apache.catalina.users.SparseUserDatabase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.core.Local;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.missionuplink.admindashboard.model.entity.AppUser;

@RestController
@RequestMapping("/api/user")
public class UserController {

    private UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }


    @GetMapping(value={"/new"})
    public List<AppUser> getAllWithinDate() {
        LocalDate fiveDaysAgo = LocalDate.now().minusDays(5);
        return userService.getAllWithDate(fiveDaysAgo);
    }

    @PutMapping("{id}/updateinfo")
    public ResponseEntity<String> updateInfo(@PathVariable long id, @RequestBody UpdateUserInfoDto updateUserInfoDtoDto){
        String response = userService.updateInfo(id, updateUserInfoDtoDto);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PutMapping("{id}/modifystatus")
    public ResponseEntity<String> modifyStatus(@PathVariable long id, @RequestBody AppUserStatus appUserStatus){
        String response = userService.modifyStatus(id, appUserStatus);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    // // Login/Signin REST API
    // // url: "/api/auth/login" or "/api/auth/signin"
    // @PostMapping(value = {"/login", "/signin"})
    // public ResponseEntity<JWTAuthResponse> login(@RequestBody LoginDto loginDto){
    //     String token = authService.login(loginDto);

    //     JWTAuthResponse jwtAuthResponse = new JWTAuthResponse();
    //     jwtAuthResponse.setAccessToken(token);

    //     return ResponseEntity.ok(jwtAuthResponse);
    // }

    // // Register/Signup REST API
    // // url: "/api/auth/register" or "/api/auth/signup"
    // // currently specify the appUserRole field as "USER" or "ADMIN"
    // @PostMapping(value = {"/register", "/signup"})
    // public ResponseEntity<String> register(@RequestBody RegisterDto registerDto){
    //     String response = authService.register(registerDto);
    //     return new ResponseEntity<>(response, HttpStatus.CREATED);
    // }
}
