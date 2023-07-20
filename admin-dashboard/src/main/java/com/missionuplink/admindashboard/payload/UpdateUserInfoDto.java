package com.missionuplink.admindashboard.payload;
import java.util.List;

import com.missionuplink.admindashboard.model.enums.AppUserRole;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UpdateUserInfoDto {
    // private String firstName;
    // private String lastName;
    // private String email;
    // private String password;
    // private AppUserRole appUserRole;
    private Long averageBandwidth;
    private Long averageTimeOnline;
    private List<String> lastFourUrls;
    private List<String> topFourUrls;
}
