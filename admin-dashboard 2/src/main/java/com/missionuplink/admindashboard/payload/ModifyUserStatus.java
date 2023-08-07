package com.missionuplink.admindashboard.payload;

import com.missionuplink.admindashboard.model.enums.AppUserStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ModifyUserStatus {
//    private Long id;
    private AppUserStatus appUserStatus;
}
