package com.missionuplink.admindashboard.appuser;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.missionuplink.admindashboard.registration.token.ConfirmationToken;
import com.missionuplink.admindashboard.registration.token.ConfirmationTokenService;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Service
@AllArgsConstructor
@RestController
@RequestMapping(path = "/test")
public class AppUserService {
	
	private final AppUserRepository appUserRepository;
	
	public String resigterUser(AppUser user) {
        // More Business Logic
		appUserRepository.save(user);
        return "Success";
    }

//    private final static String USER_NOT_FOUND_MSG = 
//            "User with email %s not found";
//
    private final ConfirmationTokenService confirmationTokenService;
//
//    @Override
//    public UserDetails loadUserByUsername(String email) 
//            throws UsernameNotFoundException {
//        
//        return appUserRepository.findByEmail(email)
//                .orElseThrow(() -> 
//                         new UsernameNotFoundException(
//                                String.format(USER_NOT_FOUND_MSG, email)));
//    }
//    
    public String signUpUser(AppUser appUser) {
        boolean userExists = appUserRepository
                .findByEmail(appUser.getEmail())
                .isPresent();
        if (userExists) {
            throw new IllegalStateException("email already exists");
        }   
        
        
        appUserRepository.save(appUser);

        String token = UUID.randomUUID().toString();
        
        ConfirmationToken confirmationToken = new ConfirmationToken(
            token, 
            LocalDateTime.now(),
            LocalDateTime.now().plusMinutes(15),
            appUser
        );

        confirmationTokenService.saveConfirmationToken(
                confirmationToken);


        return token;
    }

    public int enableAppUser(String email) {
        return appUserRepository.enableAppUser(email);
    }

    @GetMapping("/users")
    public List<AppUser> getUsers(){
        return appUserRepository.findAll();
    }

    public void resetPassword(String email, String password) {
        Optional<AppUser> optionalAppUser = appUserRepository.findByEmail(email);
        if (optionalAppUser.isPresent()) {
            AppUser appUser = optionalAppUser.get();
            appUser.setPassword(password);
            appUserRepository.save(appUser);
        } else {
            System.out.println("User not found");
        }
    }

    record ResetPassWordRequest(
            String email,
            String password
    ) {}

    @PostMapping("/reset")
    public void resetPasswordForUser(@RequestBody ResetPassWordRequest request) {
        resetPassword(request.email, request.password);
    }


//    public void updatePassword(AppUser appUser, String password) {
//        appUser.setPassword(password);
//        appUserRepository.save(appUser);
//    }
}
