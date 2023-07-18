package com.missionuplink.admindashboard.testTools;

import com.missionuplink.admindashboard.model.entity.AppUser;
import com.missionuplink.admindashboard.payload.LoginDto;
import com.missionuplink.admindashboard.repository.AppUserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

// The following APIs are used for testing JWT Authentication only.
@RestController
@RequestMapping("/api/test")
public class JwtAuthenticationTestAPI {
    AppUserRepository appUserRepository;

    public JwtAuthenticationTestAPI(AppUserRepository appUserRepository) {
        this.appUserRepository = appUserRepository;
    }

    // any user can use this endpoint
    // Return the email and password of user back to user
    @GetMapping("/reflect")
    public ResponseEntity<LoginDto> receivedFromClient(@RequestBody LoginDto loginDto){
//        System.out.println(appUserRepository.existsByEmail("yang@gmail.com"));
//        AppUser appUser = appUserRepository.findByEmail("yang@gmail.com")
//                .orElseThrow(() ->
//                        new UsernameNotFoundException("Email not found with email: " + "yang@gmail.com"));
//        System.out.println(appUser.getFirstName());
//        System.out.println("!!!!!!!!");

        return new ResponseEntity<>(loginDto, HttpStatus.OK);
    }

    // only user with role of ADMIN can access this endpoint
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping("/testpost")
    public ResponseEntity<String> post(@RequestBody LoginDto loginDto){
        String response = "post successfully! ; " + loginDto.getEmail() + "; " + loginDto.getPassword();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
