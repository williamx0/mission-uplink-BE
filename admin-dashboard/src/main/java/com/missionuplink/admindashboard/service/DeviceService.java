package com.missionuplink.admindashboard.service;

import com.missionuplink.admindashboard.model.entity.AppUser;
import com.missionuplink.admindashboard.model.entity.Device;

import java.util.List;
import java.util.Set;

import java.util.List;

public interface DeviceService {
    Device registerDevice(Device device);
    Device updateDevice(Device device);
    void deleteDevice(Long deviceId);
    Device getDeviceById(Long deviceId);
    List<Device> getAllDevices();

    String disableDevice(Long deviceId);
    String enableDevice(Long deviceId);

    String addUser(Long deviceId, AppUser newUser);
}

