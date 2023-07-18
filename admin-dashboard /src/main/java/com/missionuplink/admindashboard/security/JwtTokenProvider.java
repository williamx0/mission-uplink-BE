package com.missionuplink.admindashboard.security;

import com.missionuplink.admindashboard.exception.AuthApiException;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;

@Component
public class JwtTokenProvider {

    // see if there is a bug !!!
    @Value("${app.jwt-secret}")
    private String jwtSecretKey;

    @Value("${app.jwt-expiration-milliseconds}")
    private long jwtExpirationDate;

    // generate JWT token
    public String generateToken(Authentication authentication){
        String username = authentication.getName();

        Date currentDate = new Date();

        Date expireDate = new Date(currentDate.getTime() + jwtExpirationDate);

        String jwtToken = Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(expireDate)
                .signWith(getJwtSecretKey())
                .compact();

        return  jwtToken;
    }

    private Key getJwtSecretKey(){
        return Keys.hmacShaKeyFor(
                Decoders.BASE64.decode(jwtSecretKey)
        );
    }

    // get username from JWT token
    public String getUsernameFromToken(String token){
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(getJwtSecretKey())
                .build()
                .parseClaimsJws(token)
                .getBody();

        String username = claims.getSubject();
        return  username;
    }

    // validate JWT token
    public boolean validateJwtToken(String token){
        try {
            Jwts.parserBuilder()
                    .setSigningKey(getJwtSecretKey())
                    .build()
                    .parse(token);
            return true;
        } catch (MalformedJwtException ex){
            throw new AuthApiException(HttpStatus.BAD_REQUEST, "Invalid JWT token");
        } catch (ExpiredJwtException ex){
            throw new AuthApiException(HttpStatus.BAD_REQUEST, "Expired JWT token");
        } catch (UnsupportedJwtException ex){
            throw new AuthApiException(HttpStatus.BAD_REQUEST, "Unsupported JWT token");
        } catch (IllegalArgumentException ex){
            throw new AuthApiException(HttpStatus.BAD_REQUEST, "JWT claims string is empty.");
        }
    }
}
