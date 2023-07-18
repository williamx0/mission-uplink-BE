package com.missionuplink.admindashboard.payload;

import com.missionuplink.admindashboard.model.enums.AppUserRole;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class RegisterDto {
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private AppUserRole appUserRole;
}
