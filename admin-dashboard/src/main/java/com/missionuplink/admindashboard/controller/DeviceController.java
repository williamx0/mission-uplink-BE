package com.missionuplink.admindashboard.controller;

import com.missionuplink.admindashboard.model.entity.Device;
import com.missionuplink.admindashboard.service.DeviceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/device_registration")
public class DeviceController {

    private final DeviceService deviceService;

    @Autowired
    public DeviceController(DeviceService deviceService) {
        this.deviceService = deviceService;
    }

    @PostMapping("/device")
    public ResponseEntity<Device> registerDevice(@RequestBody Device device) {
        Device registeredDevice = deviceService.registerDevice(device);
        return new ResponseEntity<>(registeredDevice, HttpStatus.CREATED);
    }

    @PutMapping("/{deviceId}")
    public ResponseEntity<Device> updateDevice(@PathVariable Long deviceId, @RequestBody Device device) {
        Device existingDevice = deviceService.getDeviceById(deviceId);
        if (existingDevice == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        device.setId(deviceId);
        Device updatedDevice = deviceService.updateDevice(device);
        return new ResponseEntity<>(updatedDevice, HttpStatus.OK);
    }

    @DeleteMapping("/{deviceId}")
    public ResponseEntity<Void> deleteDevice(@PathVariable Long deviceId) {
        Device existingDevice = deviceService.getDeviceById(deviceId);
        if (existingDevice == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        deviceService.deleteDevice(deviceId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/{deviceId}")
    public ResponseEntity<Device> getDeviceById(@PathVariable Long deviceId) {
        Device device = deviceService.getDeviceById(deviceId);
        if (device == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(device, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<Device>> getAllDevices() {
        List<Device> devices = deviceService.getAllDevices();
        return new ResponseEntity<>(devices, HttpStatus.OK);
    }


}
