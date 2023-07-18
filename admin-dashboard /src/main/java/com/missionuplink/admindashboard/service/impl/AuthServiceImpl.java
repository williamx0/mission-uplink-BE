package com.missionuplink.admindashboard.service.impl;

import com.missionuplink.admindashboard.exception.AuthApiException;
import com.missionuplink.admindashboard.model.entity.AppUser;
import com.missionuplink.admindashboard.payload.LoginDto;
import com.missionuplink.admindashboard.payload.RegisterDto;
import com.missionuplink.admindashboard.repository.AppUserRepository;
import com.missionuplink.admindashboard.security.JwtTokenProvider;
import com.missionuplink.admindashboard.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImpl implements AuthService {

    private AuthenticationManager authenticationManager;
    private AppUserRepository appUserRepository;
    private PasswordEncoder passwordEncoder;
    private JwtTokenProvider jwtTokenProvider;


    @Autowired
    public AuthServiceImpl(AuthenticationManager authenticationManager,
                           AppUserRepository appUserRepository,
                           PasswordEncoder passwordEncoder,
                           JwtTokenProvider jwtTokenProvider) {
        this.authenticationManager = authenticationManager;
        this.appUserRepository = appUserRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @Override
    public String login(LoginDto loginDto) {

        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                loginDto.getEmail(), loginDto.getPassword()
        ));

        SecurityContextHolder.getContext().setAuthentication(authentication);

        String token = jwtTokenProvider.generateToken(authentication);
        //return "User Logged-in successful!";
        return token;
    }

    @Override
    public String register(RegisterDto registerDto) {

        // check for email exists in database
        if(appUserRepository.existsByEmail(registerDto.getEmail())){
            throw new AuthApiException(HttpStatus.BAD_REQUEST, "Email already exists!");
        }

        AppUser appUser = new AppUser();

        appUser.setEmail(registerDto.getEmail());
        appUser.setPassword(passwordEncoder.encode(registerDto.getPassword()));

        appUser.setFirstName(registerDto.getFirstName());
        appUser.setLastName(registerDto.getLastName());
        appUser.setEnabled(true);
        appUser.setLocked(false);
        appUser.setAppUserRole(registerDto.getAppUserRole());

        appUserRepository.save(appUser);

        return "User registered successfully!";
    }
}
