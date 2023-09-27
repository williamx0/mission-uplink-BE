package com.missionuplink.admindashboard.model.entity;

import com.missionuplink.admindashboard.model.enums.AppUserRole;
import jakarta.persistence.*;
import java.sql.Timestamp;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.time.LocalDate;
import java.util.List;

// import org.hibernate.mapping.List;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "Mission Uplink Teachers and Admins")
public class AppUser {
    @SequenceGenerator(
            name = "user_sequence",
            sequenceName = "user_sequence",
            allocationSize = 1
    )
    @Id
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "user_sequence"
    )
    private Long id;

    @Column(nullable = false)
    private String firstName;

    @Column(nullable = false)
    private String lastName;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    private Boolean locked;

    private Boolean enabled;

    private Boolean temporary;

    private Boolean online;

    private LocalDate creationDate;

//     @Column(nullable = false)
    private Long averageBandwidth;
    
//     @Column(nullable = false)
    private Long averageTimeOnline;

    private String assignedLab;

    private Timestamp lastOnline;

    private String status;

    private String type;

    private String validUntil;

    private List<String> lastFourUrls;

    private List<String> topFourUrls;

//    @Column(nullable = false)
//    @Enumerated(EnumType.STRING)
//    private AppUserStatus appUserStatus;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private AppUserRole appUserRole;
}
