package com.missionuplink.admindashboard.service;

import com.missionuplink.admindashboard.model.entity.AppUser;
import com.missionuplink.admindashboard.model.entity.Device;
import com.missionuplink.admindashboard.payload.DeviceDto;

import java.util.List;
import java.util.Set;

import java.util.List;

public interface DeviceService {
    Device registerDevice(Device device);
    DeviceDto updateDevice(Long deviceId, DeviceDto deviceDto);
    void deleteDevice(Long deviceId);
    Device getDeviceById(Long deviceId);
    List<Device> getAllDevices();

    String disableDevice(Long deviceId);
    String enableDevice(Long deviceId);

    String addUser(Long deviceId, AppUser newUser);
}

