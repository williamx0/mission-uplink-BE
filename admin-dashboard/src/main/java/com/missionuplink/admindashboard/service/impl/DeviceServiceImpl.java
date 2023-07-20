package com.missionuplink.admindashboard.service.impl;

import com.missionuplink.admindashboard.exception.AdminException;
import com.missionuplink.admindashboard.exception.ResourceNotFoundException;
import com.missionuplink.admindashboard.model.entity.AppUser;
import com.missionuplink.admindashboard.model.entity.Device;
import com.missionuplink.admindashboard.payload.DeviceDto;
import com.missionuplink.admindashboard.repository.DeviceRepository;
import com.missionuplink.admindashboard.service.DeviceService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;



@Service
public class DeviceServiceImpl implements DeviceService {

        private final DeviceRepository deviceRepository;

        @Autowired
        public DeviceServiceImpl(DeviceRepository deviceRepository) {
            this.deviceRepository = deviceRepository;
        }

        @Override
        public Device registerDevice(Device device) {
            return deviceRepository.save(device);
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
        public List<Device> getAllDevices() {
            return deviceRepository.findAll();
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
                throw new AdminException(HttpStatus.BAD_REQUEST, "User with id " + newUser.getId() + "is already registered with device " + deviceId + "!");
            }

            device.getAppUser().add(newUser);
            deviceRepository.save(device);
            return "User with id " + newUser.getId() + "is successfully  with device " + deviceId + "!";
        }

        @Override
        public List<Device> getNewDevicesBetweenDays(LocalDate desiredDate, LocalDate currentDate) {

            return deviceRepository.findAllByRegistrationDateBetween(desiredDate,currentDate);
        }
}
