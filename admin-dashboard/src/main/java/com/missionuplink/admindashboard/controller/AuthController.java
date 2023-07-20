package com.missionuplink.admindashboard.controller;

import com.missionuplink.admindashboard.payload.JWTAuthResponse;
import com.missionuplink.admindashboard.payload.LoginDto;
import com.missionuplink.admindashboard.payload.RegisterDto;
import com.missionuplink.admindashboard.payload.UpdateUserInfoDto;
import com.missionuplink.admindashboard.service.AuthService;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.core.Local;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.missionuplink.admindashboard.model.entity.AppUser;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private AuthService authService;

    @Autowired
    public AuthController(AuthService authService) {
        this.authService = authService;
    }


    @GetMapping(value={"/new"})
    public List<AppUser> getAllWithinDate() {
        LocalDate fiveDaysAgo = LocalDate.now().minusDays(5);
        return authService.getAllWithDate(fiveDaysAgo);
    }

    @PutMapping("{id}/updateinfo")
    public ResponseEntity<String> updateInfo(@PathVariable long id, @RequestBody UpdateUserInfoDto updateUserInfoDtoDto){
        String response = authService.updateInfo(id, updateUserInfoDtoDto);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }
    // Login/Signin REST API
    // url: "/api/auth/login" or "/api/auth/signin"
    @PostMapping(value = {"/login", "/signin"})
    public ResponseEntity<JWTAuthResponse> login(@RequestBody LoginDto loginDto){
        String token = authService.login(loginDto);

        JWTAuthResponse jwtAuthResponse = new JWTAuthResponse();
        jwtAuthResponse.setAccessToken(token);

        return ResponseEntity.ok(jwtAuthResponse);
    }

    // Register/Signup REST API
    // url: "/api/auth/register" or "/api/auth/signup"
    // currently specify the appUserRole field as "USER" or "ADMIN"
    @PostMapping(value = {"/register", "/signup"})
    public ResponseEntity<String> register(@RequestBody RegisterDto registerDto){
        String response = authService.register(registerDto);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }
}
