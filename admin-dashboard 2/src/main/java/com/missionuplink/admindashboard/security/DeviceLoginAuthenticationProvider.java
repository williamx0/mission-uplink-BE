//package com.missionuplink.admindashboard.security;
//
//import com.missionuplink.admindashboard.exception.MacAddressNotFoundException;
//import com.missionuplink.admindashboard.model.entity.Device;
//import com.missionuplink.admindashboard.repository.DeviceRepository;
//import lombok.RequiredArgsConstructor;
//import org.springframework.http.HttpStatus;
//import org.springframework.security.authentication.AuthenticationProvider;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.AuthenticationException;
//
//@RequiredArgsConstructor
//public class DeviceLoginAuthenticationProvider implements AuthenticationProvider{
//
//    private final DeviceRepository deviceRepository;
//
//    @Override
//    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
//        String username = authentication.getName();
//        String password = authentication.getCredentials().toString();
//        String macId = ((DeviceLoginToken) authentication).getMacId();
//
//        Device device = deviceRepository.findByMacAddress(macId);
//        if (device == null) throw new MacAddressNotFoundException(HttpStatus.BAD_REQUEST, "Device with Mac Address" + macId + "not found.");
//
//        return new UsernamePasswordAuthenticationToken(username, password, null);
//    }
//
//    @Override
//    public boolean supports(Class<?> authentication) {
//        return DeviceLoginAuthenticationProvider.class.isAssignableFrom(authentication);
//    }
//}
