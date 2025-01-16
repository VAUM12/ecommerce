package com.qalaa.user.reposotory;

import com.qalaa.user.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmailAndOtp(String email, String otp);

    Optional<User> findByEmail(String email);

    Optional<User> findByResetToken(String token);
}
