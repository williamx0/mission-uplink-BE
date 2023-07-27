package com.missionuplink.admindashboard.payload;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DeviceLoginDto {
	private String username;
    private String password;
    private String MacID;
}
