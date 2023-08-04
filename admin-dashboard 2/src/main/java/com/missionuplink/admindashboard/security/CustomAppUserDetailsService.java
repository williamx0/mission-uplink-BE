package com.missionuplink.admindashboard.security;


import com.missionuplink.admindashboard.model.entity.AppUser;
import com.missionuplink.admindashboard.repository.AppUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;
import com.missionuplink.admindashboard.model.enums.AppUserStatus;

@Service
public class CustomAppUserDetailsService implements UserDetailsService {

    private AppUserRepository appUserRepository;

    @Autowired
    public CustomAppUserDetailsService(AppUserRepository appUserRepository) {
        this.appUserRepository = appUserRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        // If the user with the given email in database, the exception will be thrown
        AppUser appUser = appUserRepository.findByEmail(email)
                .orElseThrow(() ->
                        new UsernameNotFoundException("Email not found with email: " + email));

        Set<GrantedAuthority> authorities = new HashSet<>();
        switch (appUser.getAppUserRole()){
            case TEACHER :
                // only some specific role's name can be used to new SimpleGrantedAuthority object
                authorities.add(new SimpleGrantedAuthority("ROLE_TEACHER"));
                break;
            case ADMIN :
                authorities.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
                break;
        }

        AppUserStatus userStatus = appUser.getAppUserStatus();
        switch (userStatus) {
            case ONLINE:
                appUser.setAppUserStatus(AppUserStatus.ONLINE);
                break;
            case OFFLINE:
                appUser.setAppUserStatus(AppUserStatus.OFFLINE);
                break;
            case DISABLED:
                appUser.setAppUserStatus(AppUserStatus.DISABLED);
                break;
            default:
                // Handle any unexpected cases
                break;
//            appUser.setAppUserStatus(AppUserStatus.ONLINE);
//            case ONLINE:
//                authorities.add(new SimpleGrantedAuthority("STATUS_ONLINE"));
//                break;
//            case OFFLINE:
//                authorities.add(new SimpleGrantedAuthority("STATUS_OFFLINE"));
//                break;
//            case DISABLED:
//                authorities.add(new SimpleGrantedAuthority("STATUS_DISABLED"));
//                break;
        }

        return new org.springframework.security.core.userdetails.User(
                appUser.getEmail(),
                appUser.getPassword(),
                authorities);
    }
}
