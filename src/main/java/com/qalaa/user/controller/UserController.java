package com.qalaa.user.controller;

import com.qalaa.user.model.User;
import com.qalaa.user.service.UserService;
import com.qalaa.user.wrapper.UserWrapper;
import jakarta.validation.constraints.Email;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public ResponseEntity<Object> register(@RequestBody UserWrapper userWrapper) {
        User registeredUser= userService.registerUser(userWrapper);
        return ResponseEntity.ok("User registered. OTP sent to: " + registeredUser.getMobileNumber());
    }

    @PostMapping("/verify-otp")
    private ResponseEntity<Object> verifyOtp(@RequestParam @Email String email, @RequestParam String otp) {
        UserWrapper userWrapper = userService.verifyOtp(email, otp);
        if(userWrapper == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid OTP.");
        }
        return ResponseEntity.ok(userWrapper);

    }
}
