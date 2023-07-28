//package com.missionuplink.admindashboard.security;
//
//import com.missionuplink.admindashboard.exception.MacAddressNotFoundException;
//import com.missionuplink.admindashboard.model.entity.Device;
//import com.missionuplink.admindashboard.repository.DeviceRepository;
//import jakarta.servlet.FilterChain;
//import jakarta.servlet.ServletException;
//import java.io.BufferedReader;
//import java.io.IOException;
//import lombok.RequiredArgsConstructor;
//import org.springframework.http.HttpStatus;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.AuthenticationException;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
//import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
//import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;
//import org.springframework.security.web.authentication.preauth.PreAuthenticatedCredentialsNotFoundException;
//import org.springframework.security.web.util.matcher.RequestMatcher;
//
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
//import org.springframework.stereotype.Component;
//import org.springframework.web.filter.OncePerRequestFilter;
//import com.fasterxml.jackson.databind.JsonNode;
//import com.fasterxml.jackson.databind.ObjectMapper;
//
//@Component
//@RequiredArgsConstructor
//public class MacAddressAuthenticationFilter extends OncePerRequestFilter {
//
//    private final DeviceRepository deviceRepository;
//
//    @Override
//    protected void doFilterInternal(HttpServletRequest request,
//            HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
//
//        WebAuthenticationDetailsSource authenticationDetailsSource = new WebAuthenticationDetailsSource();
//
//        // Extract MAC address from the request headers
//        String macAddress = extractMacAddressFromRequest(request);
//
//        // Validate the MAC address
//        if (!isValidMacAddress(macAddress)) {
//            throw new PreAuthenticatedCredentialsNotFoundException("Invalid MAC address");
//        }
//
//        // If MAC address is valid, create an authentication token and proceed with the filter chain
//        PreAuthenticatedAuthenticationToken authenticationToken = new PreAuthenticatedAuthenticationToken(macAddress, null);
//        authenticationToken.setDetails(authenticationDetailsSource.buildDetails(request));
//        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
//
//        // Continue with the filter chain
//        filterChain.doFilter(request, response);
//    }
//
//    private String extractMacAddressFromRequest(HttpServletRequest request) throws IOException {
//        StringBuilder sb = new StringBuilder();
//        BufferedReader reader = request.getReader();
//
//        String line;
//        while ((line = reader.readLine()) != null) {
//            sb.append(line);
//        }
//
//        String requestBody = sb.toString();
//
//        // Use Jackson ObjectMapper to parse the request body into a JsonNode
//        ObjectMapper objectMapper = new ObjectMapper();
//        JsonNode jsonNode = objectMapper.readTree(requestBody);
//
//        // Extract the value of the "macAddress" field from the JsonNode
//        String macAddress = jsonNode.get("macAddress").asText();
//
//        return macAddress;
//    }
//
//    private boolean isValidMacAddress(String macAddress) {
//        Device device = deviceRepository.findByMacAddress(macAddress);
//        return device == null;
//    }
//
//
//}
//
