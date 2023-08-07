package com.missionuplink.admindashboard.payload;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AddUserDto {
    private String firstName;
    private String lastName;
    private String type;
    private String assignedLab;
    private String validUntil;
    private String status;
    private boolean temporary;
}
