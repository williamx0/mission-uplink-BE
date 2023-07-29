package com.missionuplink.admindashboard.repository;

import com.missionuplink.admindashboard.model.entity.Device;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface DeviceRepository extends JpaRepository<Device, Long> {
    Optional<Device> findById(Long id);
    List<Device> findAllByRegistrationDateBetween(
        LocalDate desiredDate,
        LocalDate currentDate);

    Device findByMacAddress(String macAddress);

    Boolean existsByMacAddress(String macAddress);
}
