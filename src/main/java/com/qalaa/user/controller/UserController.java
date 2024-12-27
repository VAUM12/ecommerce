package com.qalaa.user.controller;

import com.qalaa.user.model.User;
import com.qalaa.user.service.UserService;
import com.qalaa.user.wrapper.UserWrapper;
import com.qalaa.util.ApiResponse;
import jakarta.servlet.http.HttpServletRequest;
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
    public ResponseEntity<ApiResponse<String>> register(@RequestBody UserWrapper userWrapper, HttpServletRequest request) {
        User registeredUser= userService.registerUser(userWrapper);
        return ResponseEntity.ok(new ApiResponse<>("success", "User registered. OTP sent to: " + registeredUser.getMobileNumber(), null, request.getRequestURI()));
    }

    @PostMapping("/verify-otp")
    public ResponseEntity<ApiResponse<UserWrapper>> verifyOtp(@RequestParam @Email String email, @RequestParam String otp,HttpServletRequest request) {
        UserWrapper userWrapper = userService.verifyOtp(email, otp);
        return ResponseEntity.ok(new ApiResponse<>("success", "OTP verified successfully", userWrapper, request.getRequestURI()));

    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<UserWrapper>>login(@RequestParam String email,@RequestParam String password,HttpServletRequest request ){
        return ResponseEntity.ok(new ApiResponse<>("success", "Login successful", userService.login(email, password), "/api/user/login"));
    }

    @PostMapping("/resend-otp")
    public ResponseEntity<ApiResponse<UserWrapper>>SendOtp(@RequestParam @Email String email,HttpServletRequest request) {
        return ResponseEntity.ok(new ApiResponse<>("success", "OTP resent successfully", userService.sendOtp(email), "/api/user/resend-otp"));
    }

    @PostMapping("/forgot-password-send-otp")
    public ResponseEntity<ApiResponse<UserWrapper>> forgotPassword(@RequestParam @Email String email,HttpServletRequest request) {
        return ResponseEntity.ok(new ApiResponse<>("success", "OTP sent for password reset", userService.sendOtpForgotPassword(email), "/api/user/forgot-password-send-otp"));
    }

    @PostMapping("/forgot-password-verify-otp")
    public ResponseEntity<ApiResponse<UserWrapper>> forgotPasswordVerifyOtp(@RequestParam @Email String email, @RequestParam String otp, @RequestParam String newPassword,HttpServletRequest request) {
        UserWrapper userWrapper = userService.updatePassword(email, otp,newPassword);

        return ResponseEntity.ok(new ApiResponse<>("success", "OTP verified successfully", userWrapper, request.getRequestURI()));
    }
}
