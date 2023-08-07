package com.missionuplink.admindashboard.model.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import java.util.Calendar;
import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "Reset Password Token")
public class PasswordResetToken {
    @SequenceGenerator(
            name = "reset_password_token_sequence",
            sequenceName = "reset_password_token_sequence",
            allocationSize = 1
    )
    @Id
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "reset_password_token_sequence"
    )
    private Long token_id;

    private String token;
    private Date expirationTime;
    private static final int EXPIRATION_TIME = 5; // Token is valid for 5 minutes

    @OneToOne
    @JoinColumn(name = "user_id")
    private AppUser appUser;

    public PasswordResetToken(String token, AppUser appUser) {
        super();
        this.token = token;
        this.appUser = appUser;
        this.expirationTime = this.getTokenExpirationTime();
    }

    public PasswordResetToken(String token) {
        super();
        this.token = token;
         this.expirationTime = this.getTokenExpirationTime();
    }

    /**
     * Calculate the time that the newly generated token will be expired
     * Currently the expiration time is 5 minutes
     * @return a Date object that indicates the time that the token will be expired
     */
    public Date getTokenExpirationTime() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(new Date().getTime());
        calendar.add(Calendar.MINUTE, EXPIRATION_TIME);
        return new Date(calendar.getTime().getTime());
    }
}
