package com.missionuplink.admindashboard.service.impl;

import com.missionuplink.admindashboard.exception.AuthApiException;
import com.missionuplink.admindashboard.model.entity.AppUser;
import com.missionuplink.admindashboard.model.enums.AppUserRole;
import com.missionuplink.admindashboard.payload.DeviceLoginDto;
import com.missionuplink.admindashboard.payload.LoginDto;
import com.missionuplink.admindashboard.payload.RegisterDto;
import com.missionuplink.admindashboard.payload.UpdateUserInfoDto;
import com.missionuplink.admindashboard.repository.AppUserRepository;
import com.missionuplink.admindashboard.security.JwtTokenProvider;
import com.missionuplink.admindashboard.service.AuthService;
import com.missionuplink.admindashboard.service.PasswordResetTokenService;
import java.util.Optional;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Optional;
import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private AuthenticationManager authenticationManager;
    private AppUserRepository appUserRepository;
    private PasswordEncoder passwordEncoder;
    private JwtTokenProvider jwtTokenProvider;
    private final PasswordResetTokenService passwordResetTokenService;


    @Autowired
    public AuthServiceImpl(AuthenticationManager authenticationManager,
                           AppUserRepository appUserRepository,
                           PasswordEncoder passwordEncoder,
                           JwtTokenProvider jwtTokenProvider,
            PasswordResetTokenService passwordResetTokenService) {
        this.authenticationManager = authenticationManager;
        this.appUserRepository = appUserRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtTokenProvider = jwtTokenProvider;
        this.passwordResetTokenService = passwordResetTokenService;
    }

    @Override
    public String[] login(LoginDto loginDto) {

        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                loginDto.getEmail(), loginDto.getPassword()
        ));

        SecurityContextHolder.getContext().setAuthentication(authentication);

        String token = jwtTokenProvider.generateToken(authentication);
        AppUserRole appUserRole = appUserRepository.findByEmail(loginDto.getEmail()).get().getAppUserRole();
        //return "User Logged-in successful!";
        return new String[]{token, appUserRole.toString()};
    }
    
    //implement this, should be similar to login function but we dont need to return the appUserRole. Just the JWT token
    @Override
    public String deviceLogin(DeviceLoginDto loginDto) {
    	return null;
    }

    // @Override
    public List<AppUser> getAllWithDate(LocalDate fiveDaysAgo) {
        return appUserRepository.findAllWithDate(fiveDaysAgo);
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
        appUser.setCreationDate(LocalDate.now());
        appUser.setAverageBandwidth(null);
        // appUser.setAverageTimeOnline();
        appUser.setAverageTimeOnline(null);
        appUser.setLastFourUrls(null);
        appUser.setTopFourUrls(null);

        appUserRepository.save(appUser);

        return "User registered successfully!";
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
    public Optional<AppUser> findUserByEmail(String email) {
        return appUserRepository.findByEmail(email);
    }

    @Override
    public void createPasswordResetTokenForUser(String token, AppUser appUser) {
        passwordResetTokenService.createPasswordResetTokenForUser(token, appUser);
    }

    @Override
    public String validatePasswordResetToken(String token) {
        return passwordResetTokenService.validatePasswordResetToken(token);
    }

    @Override
    public AppUser findUserByPasswordResetToken(String token) {
        return passwordResetTokenService.findAppUserByPasswordResetToken(token).get() ;
    }

    @Override
    public void resetUserPassword(AppUser appUser, String newPassword) {
        appUser.setPassword(passwordEncoder.encode(newPassword));
        appUserRepository.save(appUser);
    }

}
