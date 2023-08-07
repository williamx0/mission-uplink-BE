package com.missionuplink.admindashboard.service;

import com.missionuplink.admindashboard.model.entity.AppUser;
import com.missionuplink.admindashboard.model.entity.Device;
import com.missionuplink.admindashboard.payload.DeviceDto;
import com.missionuplink.admindashboard.payload.DeviceResponse;
import com.missionuplink.admindashboard.payload.DeviceStatusDto;

import java.util.List;
import java.util.Set;
import java.time.LocalDate;
import java.util.List;

public interface DeviceService {
    DeviceStatusDto registerDevice(DeviceDto deviceDto);
    DeviceDto updateDevice(Long deviceId, DeviceDto deviceDto);
    void deleteDevice(Long deviceId);
    Device getDeviceById(Long deviceId);
    DeviceResponse getAllDevices(int pageNo, int pageSize);

    String disableDevice(Long deviceId);
    String enableDevice(Long deviceId);
    List<Device> getNewDevicesBetweenDays(LocalDate desiredDate, LocalDate currentDate);
    String addUser(Long deviceId, AppUser newUser);
    DeviceStatusDto updateDeviceStatus(DeviceStatusDto deviceStatusDto);

    void deleteById(Long id);

}

