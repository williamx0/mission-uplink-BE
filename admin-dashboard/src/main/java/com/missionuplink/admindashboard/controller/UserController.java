package com.missionuplink.admindashboard.controller;

import com.missionuplink.admindashboard.payload.*;
import com.missionuplink.admindashboard.repository.AppUserRepository;
import com.missionuplink.admindashboard.repository.UserRepository;
import com.missionuplink.admindashboard.service.UserService;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.missionuplink.admindashboard.model.entity.AppUser;


@RestController
@RequestMapping("/api/user")
public class UserController {

    private UserService userService;

    private final AppUserRepository appUserRepository;

    private final UserRepository userRepository;

    @Autowired
    public UserController(UserService userService, AppUserRepository appUserRepository, UserRepository userRepository) {
        this.userService = userService;
        this.appUserRepository = appUserRepository;
        this.userRepository = userRepository;
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

    @PutMapping("{id}/modifystatus")
    public ResponseEntity<String> modifyStatus(@PathVariable long id, @RequestBody AppUserStatusDto appUserStatusDto){
        String response = userService.modifyStatus(id, appUserStatusDto);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping("/all")
    public Page<AppUser> all(@RequestParam int page, @RequestParam int size) {
        PageRequest pageRequest = PageRequest.of(page, size);
        return appUserRepository.findAll(pageRequest);
    }

    @PostMapping("/add")
    public ResponseEntity<AddUserResponse> add(@RequestBody AddUserDto addUserDto) {
        AddUserResponse response = userService.add(addUserDto);
        return ResponseEntity.ok(response);
    }
}
   