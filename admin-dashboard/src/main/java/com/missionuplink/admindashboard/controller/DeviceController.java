package com.missionuplink.admindashboard.controller;

import com.missionuplink.admindashboard.exception.ResourceNotFoundException;
import com.missionuplink.admindashboard.model.entity.AppUser;
import com.missionuplink.admindashboard.model.entity.Device;
import com.missionuplink.admindashboard.payload.DeviceDto;
import com.missionuplink.admindashboard.payload.DeviceResponse;
import com.missionuplink.admindashboard.payload.DeviceStatusDto;
import com.missionuplink.admindashboard.repository.AppUserRepository;
import com.missionuplink.admindashboard.service.DeviceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDate;
import java.util.List;


@RestController
@RequestMapping("/api/device_registration")
public class DeviceController {

    private final DeviceService deviceService;
    private final AppUserRepository appUserRepository;

    @Autowired
    public DeviceController(DeviceService deviceService,
                            AppUserRepository appUserRepository) {
        this.deviceService = deviceService;
        this.appUserRepository = appUserRepository;
    }

    //add new device
    @PostMapping("/devices/add")
    public ResponseEntity<DeviceStatusDto> registerDevice(@RequestBody DeviceDto deviceDto) {
        DeviceStatusDto response = deviceService.registerDevice(deviceDto);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    //get device status
    @PutMapping("/devices/updateStatus")
    public ResponseEntity<DeviceStatusDto> updateDeviceStatus(@RequestBody DeviceStatusDto deviceStatusDto){
        DeviceStatusDto response = deviceService.updateDeviceStatus(deviceStatusDto);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    //update info for device with deviceid
    @PutMapping("/{deviceId}")
    public ResponseEntity<DeviceDto> updateDevice(@PathVariable(value = "deviceId") Long deviceId, @RequestBody DeviceDto deviceDto) {

        DeviceDto updatedDeviceDto = deviceService.updateDevice(deviceId, deviceDto);

        return new ResponseEntity<>(updatedDeviceDto, HttpStatus.OK);
    }

    //delete a device
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

    @GetMapping("/getAll")
    public ResponseEntity<DeviceResponse> getAllDevices(
            @RequestParam(value = "pageNo", defaultValue = "0", required = false) int pageNo,
            @RequestParam(value = "pageSize", defaultValue = "8", required = false) int pageSize
    ) {
        DeviceResponse deviceResponse = deviceService.getAllDevices(pageNo, pageSize);
        return new ResponseEntity<>(deviceResponse, HttpStatus.OK);
    }
        
    @GetMapping("/newDevice")
    public ResponseEntity<List<Device>> getNewDevice() {
    	
    	LocalDate currentDate = LocalDate.now();

        // Get the date 5 days earlier
        LocalDate fiveDaysEarlier = currentDate.minusDays(5);
        List<Device> device = deviceService.getNewDevicesBetweenDays(fiveDaysEarlier, currentDate);
        if (device == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(device, HttpStatus.OK);
    }

    @PutMapping("/{deviceId}/disable")
    public ResponseEntity<String> disableDevice(@PathVariable(value = "deviceId") Long deviceId){
        String result = deviceService.disableDevice(deviceId);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @PutMapping("/{deviceId}/enable")
    public ResponseEntity<String> enableDevice(@PathVariable(value = "deviceId") Long deviceId){
        String result = deviceService.enableDevice(deviceId);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    // add user with userId to the device with deviceId
    @PutMapping("/{deviceId}/adduser/{userId}")
    public ResponseEntity<String> addUser(
            @PathVariable(value = "deviceId") Long deviceId,
            @PathVariable(value = "userId")Long userId){
        AppUser newUser = appUserRepository.findById(userId).orElseThrow(
                () -> new ResourceNotFoundException("AppUser", "id", userId)
        );

        String result = deviceService.addUser(deviceId, newUser);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @DeleteMapping("/devices/delete/{id}")
    public ResponseEntity<String> deleteById(@PathVariable(value = "id") Long id){
        deviceService.deleteById(id);
        return new ResponseEntity<>("Device deleted successfully", HttpStatus.OK);
    }


}
