package com.missionuplink.admindashboard.payload;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DeviceDto {
    private String macAddress;
    private String ipAddress;
    private String deviceType;
    private String deviceName;
    private String hardwareId;
    private String assignedLab;
    private String SystemModel;
    private String uid;
}
