package com.missionuplink.admindashboard.service.impl;

import com.missionuplink.admindashboard.model.entity.Device;
import com.missionuplink.admindashboard.repository.DeviceRepository;
import com.missionuplink.admindashboard.service.DeviceService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
        public Device updateDevice(Device device) {
            // Additional validation or business logic can be performed here if needed
            return deviceRepository.save(device);
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
}
