package com.missionuplink.admindashboard.service.impl;

import com.missionuplink.admindashboard.exception.AdminException;
import com.missionuplink.admindashboard.exception.ResourceNotFoundException;
import com.missionuplink.admindashboard.model.entity.AppUser;
import com.missionuplink.admindashboard.model.entity.Device;
import com.missionuplink.admindashboard.payload.DeviceDto;
import com.missionuplink.admindashboard.payload.DeviceResponse;
import com.missionuplink.admindashboard.payload.DeviceStatusDto;
import com.missionuplink.admindashboard.repository.DeviceRepository;
import com.missionuplink.admindashboard.service.DeviceService;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;



@Service
public class DeviceServiceImpl implements DeviceService {

        private final DeviceRepository deviceRepository;
        private final DateTimeFormatter localDateFormatter;

        @Autowired
        public DeviceServiceImpl(DeviceRepository deviceRepository) {
            this.deviceRepository = deviceRepository;
            this.localDateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");
        }

        @Override
//        public Device registerDevice(Device device) {
//            return deviceRepository.save(device);
//        }
        public DeviceStatusDto registerDevice(DeviceDto deviceDto) {
            if (deviceRepository.existsByMacAddress(deviceDto.getMacAddress())){
                throw new AdminException(HttpStatus.BAD_REQUEST, "Device with Mac Address of " + deviceDto.getMacAddress() + " has already existed.");
            };
            Device device = new Device();

            device.setMacAddress(deviceDto.getMacAddress());
            device.setDeviceName(deviceDto.getDeviceName());
            device.setHardwareId(deviceDto.getHardwareId());
            device.setDeviceType(deviceDto.getDeviceType());
            device.setIpAddress(deviceDto.getIpAddress());
            device.setAssignedLab(deviceDto.getAssignedLab());

            device.setEnabled(true);
            device.setOnline(true);

            LocalDateTime currentTime = LocalDateTime.now();
            String currentTimeString = currentTime.format(localDateFormatter);
            currentTime = LocalDateTime.parse(currentTimeString, localDateFormatter);
            device.setRegistrationDate(currentTime);
            deviceRepository.save(device);

            Device createdDevice = deviceRepository.findByMacAddress(deviceDto.getMacAddress());
            DeviceStatusDto deviceStatusDto = new DeviceStatusDto();
            deviceStatusDto.setId(createdDevice.getId());
            deviceStatusDto.setOnline(createdDevice.getOnline());
            deviceStatusDto.setEnabled(createdDevice.getEnabled());
            return deviceStatusDto;
        }

        @Override
        public DeviceDto updateDevice(Long deviceId, DeviceDto deviceDto) {
            // Additional validation or business logic can be performed here if needed
            Device device = deviceRepository.findById(deviceId).orElseThrow(
                    () -> new ResourceNotFoundException("Device", "id", deviceId)
            );

            //update info
            device.setMacAddress(deviceDto.getMacAddress());
            device.setDeviceName(deviceDto.getDeviceName());
            device.setHardwareId(deviceDto.getHardwareId());
            device.setDeviceType(deviceDto.getDeviceType());
            device.setSystemModel(deviceDto.getSystemModel());
            device.setUid(deviceDto.getUid());

            Device updatedDevice = deviceRepository.save(device);

            // return the updated deviceDTO
            DeviceDto updatedDeviceDto = new DeviceDto();
            updatedDeviceDto.setMacAddress(updatedDevice.getMacAddress());
            updatedDeviceDto.setDeviceName(updatedDevice.getDeviceName());
            updatedDeviceDto.setHardwareId(updatedDevice.getHardwareId());
            updatedDeviceDto.setDeviceType(updatedDevice.getDeviceType());
            updatedDeviceDto.setSystemModel(updatedDevice.getSystemModel());
            updatedDeviceDto.setUid(updatedDevice.getUid());

            return updatedDeviceDto;
        }

        @Override
        public void deleteDevice(Long deviceId) {
            deviceRepository.deleteById(deviceId);
        }

        @Override
        public Device getDeviceById(Long deviceId) {
            Optional<Device> optionalDevice = deviceRepository.findById(deviceId);
            return optionalDevice.orElse(null);
        }

        @Override
        public DeviceResponse getAllDevices(int pageNo, int pageSize) {

            Pageable pageable = PageRequest.of(pageNo, pageSize);
            Page<Device> devices = deviceRepository.findAll(pageable);

            //get content from page object
            List<Device> content = devices.getContent();
            DeviceResponse deviceResponse = new DeviceResponse();
            deviceResponse.setContent(content);
            deviceResponse.setPageNo(devices.getNumber());
            deviceResponse.setPageSize(devices.getSize());
            deviceResponse.setTotalDevices(devices.getTotalElements());
            deviceResponse.setTotalPages(devices.getTotalPages());
            deviceResponse.setLastPage(devices.isLast());
            return deviceResponse;
        }

        @Override
        public String disableDevice(Long deviceId) {
            Device device = deviceRepository.findById(deviceId).orElseThrow(
                    () -> new ResourceNotFoundException("Device", "id", deviceId)
            );

            device.setEnabled(false);
            Device updatedDevice = deviceRepository.save(device);
            return "Device with ID " + device.getId() + "\'s enable status is " + device.getEnabled();
        }

        @Override
        public String enableDevice(Long deviceId) {
            Device device = deviceRepository.findById(deviceId).orElseThrow(
                    () -> new ResourceNotFoundException("Device", "id", deviceId)
            );

            device.setEnabled(true);
            Device updatedDevice = deviceRepository.save(device);
            return "Device with ID " + device.getId() + "\'s enable status is " + updatedDevice.getEnabled();
        }

        @Override
        public String addUser(Long deviceId, AppUser newUser) {
            Device device = deviceRepository.findById(deviceId).orElseThrow(
                    () -> new ResourceNotFoundException("Device", "id", deviceId)
            );

            if(device.getAppUser().contains(newUser)){
                throw new AdminException(HttpStatus.BAD_REQUEST, "User with id " + newUser.getId() + " is already registered with device " + deviceId + "!");
            }

            device.getAppUser().add(newUser);
            deviceRepository.save(device);
            return "User with id " + newUser.getId() + " is successfully  with device " + deviceId + "!";
        }

        @Override
        public DeviceStatusDto updateDeviceStatus(DeviceStatusDto deviceStatusDto) {
            Device device = deviceRepository.findById(deviceStatusDto.getId()).orElseThrow(
                    () -> new ResourceNotFoundException("Device", "id", deviceStatusDto.getId())
            );

            device.setOnline(deviceStatusDto.isOnline());
            device.setEnabled(deviceStatusDto.isEnabled());
            deviceRepository.save(device);


            DeviceStatusDto updatedDeviceStatusDto = new DeviceStatusDto();
            updatedDeviceStatusDto.setId(device.getId());
            updatedDeviceStatusDto.setOnline(device.getOnline());
            updatedDeviceStatusDto.setEnabled(device.getEnabled());
            return  updatedDeviceStatusDto;
        }

        @Override
        public void deleteById(Long id) {
            Device device = deviceRepository.findById(id).orElseThrow(
                    () -> new ResourceNotFoundException("Device", "id", id)
            );

            deviceRepository.deleteById(id);
        }

    @Override
        public List<Device> getNewDevicesBetweenDays(LocalDate desiredDate, LocalDate currentDate) {

            return deviceRepository.findAllByRegistrationDateBetween(desiredDate,currentDate);
        }
}
