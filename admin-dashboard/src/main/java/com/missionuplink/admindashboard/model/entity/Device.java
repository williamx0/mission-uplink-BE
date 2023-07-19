package com.missionuplink.admindashboard.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
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

    @Column(name = "deviceName", nullable = false)
    private String deviceName;

    @Column(name = "hardwareId", nullable = false)
    private String hardwareId;

    @Column(name = "deviceType", nullable = false)
    private String deviceType;

    @Column(name = "systemModel", nullable = false)
    private String systemModel;

    @Column(nullable = false)
    private String uID;

    @Column(nullable = false)
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDate registrationDate;

    private Boolean enabled;

    @JsonIgnore
    @ManyToMany
    @JoinTable(
            name = "device_appUser",
            joinColumns = @JoinColumn(name = "device_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "appUser_id", referencedColumnName = "id")
    )
    private Set<AppUser> appUser;


}
