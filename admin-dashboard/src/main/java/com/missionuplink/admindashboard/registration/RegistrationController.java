package com.missionuplink.admindashboard.registration;

import com.missionuplink.admindashboard.appuser.AppUser;
import com.missionuplink.admindashboard.appuser.AppUserRole;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lombok.AllArgsConstructor;


@RestController
@RequestMapping(path = "/register")
@AllArgsConstructor
public class RegistrationController {

    private RegistrationService registrationService; 
    
//    @GetMapping("/get")
//    public String register(@RequestBody RegistrationRequest request) {
//        return registrationService.register(request);
//    }

    @GetMapping("/get")
    public String register() {
        return "abc";
    }

    @GetMapping("/confirm")
    public String confirm(@RequestParam("token") String token) {
       return registrationService.confirmToken(token);
    }

    record AddTestRequest(
            String firstName,
            String lastName,
            String email,
            String password,
            AppUserRole appUserRole,
            Boolean locked,
            Boolean enabled
    ){}

    @PostMapping("add")
    public void addTest(AddTestRequest addTestRequest) {
        AppUser appUser = new AppUser();
        appUser.setFirstName(addTestRequest.firstName);
        appUser.setLastName(addTestRequest.lastName);
        appUser.setEmail(addTestRequest.email);
        appUser.setPassword(addTestRequest.password);
        appUser.setAppUserRole(addTestRequest.appUserRole);
        appUser.setLocked(addTestRequest.locked);
        appUser.setEnabled(addTestRequest.enabled);
    }
}
