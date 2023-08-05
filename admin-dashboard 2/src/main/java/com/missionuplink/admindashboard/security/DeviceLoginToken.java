package com.missionuplink.admindashboard.security;

import lombok.Getter;
import lombok.Setter;
import org.springframework.security.authentication.AbstractAuthenticationToken;

@Getter
@Setter
public class DeviceLoginToken extends AbstractAuthenticationToken {
    private final String username;
    private final String password;
    private final String macId;

    public DeviceLoginToken(String username, String password, String macId) {
        super(null);
        this.username = username;
        this.password = password;
        this.macId = macId;
    }

    @Override
    public Object getCredentials() {
        return password;
    }

    @Override
    public Object getPrincipal() {
        return username;
    }
}
