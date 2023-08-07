package com.missionuplink.admindashboard.repository;

import com.missionuplink.admindashboard.model.entity.User;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository  extends JpaRepository<User, Long> {
    Optional<User> findById(String id);
}
