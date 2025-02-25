package com.qalaa.user.component;

import com.qalaa.enums.RoleEnum;
import com.qalaa.user.model.User;
import com.qalaa.user.reposotory.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class AdminDataLoader implements CommandLineRunner {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) {
        // Define specific admin users
        String[][] admins = {
                {"1234567890", "admin1@example.com", "password1"},
                {"1234567790", "admin2@example.com", "password2"},
                {"1234567990", "admin3@example.com", "password3"}
        };

        for (String[] adminData : admins) {
            String mobileNumber = adminData[0];
            String email = adminData[1];
            String password = adminData[2];

            // Check if user already exists
            if (!userRepository.existsByEmail(email)) {
                User admin = new User();
                admin.setName("admin");
                admin.setMobileNumber(mobileNumber);
                admin.setEmail(email);
                admin.setPassword(passwordEncoder.encode(password));
                admin.setVerified(true);
                admin.setRole(RoleEnum.ADMIN); // Assign ADMIN role
                userRepository.save(admin);
            }
        }

        System.out.println("Admin users initialized successfully.");
    }
}
