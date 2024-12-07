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
    public ResponseEntity<Object> verifyOtp(@RequestParam @Email String email, @RequestParam String otp) {
        UserWrapper userWrapper = userService.verifyOtp(email, otp);
        if(userWrapper == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid OTP.");
        }
        return ResponseEntity.ok(userWrapper);

    }

    @PostMapping("/login")
    public ResponseEntity<Object>login(@RequestParam String email,@RequestParam String password ){
        return ResponseEntity.ok(userService.login(email,password));
    }

    @PostMapping("/resend-otp")
    public ResponseEntity<Object>SendOtp(@RequestParam @Email String email) {
        return ResponseEntity.ok(userService.sendOtp(email));
    }

    @PostMapping("/forgot-password-send-otp")
    public ResponseEntity<Object> forgotPassword(@RequestParam @Email String email) {
        return ResponseEntity.ok(userService.sendOtpForgotPassword(email));
    }

    @PostMapping("/forgot-password-verify-otp")
    public ResponseEntity<Object> forgotPasswordVerifyOtp(@RequestParam @Email String email, @RequestParam String otp, @RequestParam String newPassword) {
        UserWrapper userWrapper = userService.updatePassword(email, otp,newPassword);
        return ResponseEntity.ok("user password updated for: " + userWrapper.getEmail());
    }
}
