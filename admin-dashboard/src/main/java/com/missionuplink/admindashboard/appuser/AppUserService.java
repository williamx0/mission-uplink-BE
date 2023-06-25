package com.missionuplink.admindashboard.appuser;

import java.time.LocalDateTime;
import java.util.UUID;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.missionuplink.admindashboard.registration.token.ConfirmationToken;
import com.missionuplink.admindashboard.registration.token.ConfirmationTokenService;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
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
}
