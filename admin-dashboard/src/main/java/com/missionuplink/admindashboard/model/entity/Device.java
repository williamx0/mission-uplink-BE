package com.missionuplink.admindashboard.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import java.time.LocalDateTime;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Set;

@Getter
@Setter
@RequiredArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="Device_Registration")
public class Device {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "macAddress", nullable = false, unique = true)
    private String macAddress;

    @Column(name = "assignedLab", nullable = false)
    private String assignedLab;

    @Column(name = "ipAddress", nullable = false)
    private String ipAddress;

    @Column(name = "deviceName", nullable = false)
    private String deviceName;

    @Column(name = "hardwareId", nullable = false)
    private String hardwareId;

    @Column(name = "deviceType", nullable = false)
    private String deviceType;

    @Column(name = "systemModel")
    private String systemModel;

    private String uid;

    @Column(nullable = false)
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime registrationDate;

    private Boolean enabled;

    private Boolean online;

    @JsonIgnore
    @ManyToMany
    @JoinTable(
            name = "device_appUser",
            joinColumns = @JoinColumn(name = "device_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "appUser_id", referencedColumnName = "id")
    )
    private Set<AppUser> appUser;


}
