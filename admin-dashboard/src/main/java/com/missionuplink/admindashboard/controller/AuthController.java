package com.missionuplink.admindashboard.controller;

import com.missionuplink.admindashboard.model.entity.AppUser;
import com.missionuplink.admindashboard.payload.DeviceLoginDto;
import com.missionuplink.admindashboard.payload.EmailDto;
import com.missionuplink.admindashboard.payload.JWTAuthResponse;
import com.missionuplink.admindashboard.payload.JWTDeviceAuthResponse;
import com.missionuplink.admindashboard.payload.LoginDto;
import com.missionuplink.admindashboard.payload.PasswordResetDto;
import com.missionuplink.admindashboard.payload.RegisterDto;
import com.missionuplink.admindashboard.payload.UpdateUserInfoDto;
import com.missionuplink.admindashboard.service.AuthService;
import jakarta.servlet.http.HttpServletRequest;
import java.util.Optional;
import java.util.UUID;
// import javax.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;

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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.missionuplink.admindashboard.model.entity.AppUser;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private AuthService authService;

    private final EmailController emailController;

    @Autowired
    public AuthController(AuthService authService, EmailController emailController) {
        this.authService = authService;
        this.emailController = emailController;
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
        String[] tokenAndRole = authService.login(loginDto);

        JWTAuthResponse jwtAuthResponse = new JWTAuthResponse();
        jwtAuthResponse.setAccessToken(tokenAndRole[0]);
        jwtAuthResponse.setAppUserRole(tokenAndRole[1]);

        return ResponseEntity.ok(jwtAuthResponse);
    }
    
    @PostMapping(value = {"/deviceLogin", "/deviceSignin"})
    public ResponseEntity<JWTDeviceAuthResponse> deviceLogin(@RequestBody DeviceLoginDto loginDto){
        String tokenAndRole = authService.deviceLogin(loginDto);

        JWTDeviceAuthResponse jWTDeviceAuthResponse = new JWTDeviceAuthResponse();
        jWTDeviceAuthResponse.setAccessToken(tokenAndRole);

        return ResponseEntity.ok(jWTDeviceAuthResponse);
    }

    // Register/Signup REST API
    // url: "/api/auth/register" or "/api/auth/signup"
    // currently specify the appUserRole field as "USER" or "ADMIN"
    @PostMapping(value = {"/register", "/signup"})
    public ResponseEntity<String> register(@RequestBody RegisterDto registerDto){
        String response = authService.register(registerDto);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    /**
     * This function is called when user clicks on the "forget password" button.
     * It generates the url that the reset password request will be sent to.
     * It also sends an email that includes that url to the user.
     * @param passwordResetDto
     * @param request
     * @return the url that the reset password request will be sent to
     */
    @PostMapping("/password-reset-request")
    public String resetPasswordRequest(
            @RequestBody PasswordResetDto passwordResetDto,
            final HttpServletRequest request
    ) {
        Optional<AppUser> appUser = authService.findUserByEmail(passwordResetDto.getEmail());
        String passwordResetURL  = "";
        if (appUser.isPresent()) {
            String token = UUID.randomUUID().toString();
            authService.createPasswordResetTokenForUser(token, appUser.get());
            passwordResetURL = passwordResetEmailLink(appUser.get(), applicationURL(request), token);
        }
        return passwordResetURL;
    }

    /**
     * Helper function that sends the email for the function above
     * @param appUser
     * @param applicationURL
     * @param token
     * @return the url that the reset password request will be sent to
     */
    private String passwordResetEmailLink(AppUser appUser, String applicationURL, String token) {
        String url = applicationURL + "api/auth/reset-password?token=" + token;
        EmailDto emailDto = new EmailDto(appUser.getEmail(), "Reset Password Link", url);
        emailController.sendForgetPasswordEmail(emailDto);
        return url;
    }

    /**
     * Helper function that generates the first have of the url
     * For example, "http://localhost:8080/"
     * @param request
     * @return the url that the reset password request will be sent to
     */
    public String applicationURL(HttpServletRequest request) {
        return "http://" + request.getServerName() + ":" + request.getServerPort() + "/" + request.getContextPath();
    }

    /**
     * This function is where the password is actually reset.
     * @param passwordResetDto
     * @param token
     * @return a string that states whether the password reset is successful.
     */
    @PostMapping("/reset-password")
    public String resetPassword(
            @RequestBody PasswordResetDto passwordResetDto,
            @RequestParam("token") String token){
        String tokenValidateResult = authService.validatePasswordResetToken(token);

        if (!tokenValidateResult.equalsIgnoreCase("valid")) return "Invalid password reset token";

        AppUser appUser = authService.findUserByPasswordResetToken(token);
        if (appUser == null) return "invalid password reset token";

        authService.resetUserPassword(appUser, passwordResetDto.getNewPassword());
        return "Password has been reset successfully";
    }
}
