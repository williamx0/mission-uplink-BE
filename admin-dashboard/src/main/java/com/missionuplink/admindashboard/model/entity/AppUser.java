package com.missionuplink.admindashboard.model.entity;

import com.missionuplink.admindashboard.model.enums.AppUserRole;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.time.LocalDate;
import java.util.List;

import org.springframework.boot.autoconfigure.web.WebProperties.Resources.Chain.Strategy;

// import org.hibernate.mapping.List;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "Mission Uplink Users")
public class AppUser {
    @SequenceGenerator(
            name = "student_sequence",
            sequenceName = "student_sequence",
            allocationSize = 1
    )
    @Id
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "student_sequence"
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

    private LocalDate creationDate;

//     @Column(nullable = false)
    private Long averageBandwidth;
    
//     @Column(nullable = false)
    private Long averageTimeOnline;

    private List<String> lastFourUrls;

    private List<String> topFourUrls;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private AppUserRole appUserRole;
}
