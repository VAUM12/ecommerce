package com.qalaa.user.controller;

import com.qalaa.user.model.User;
import com.qalaa.user.service.UserService;
import com.qalaa.user.wrapper.UserWrapper;
import com.qalaa.util.ApiResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.constraints.Email;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/api/auth/user")
public class UserAuthController {

    @Autowired
    private UserService userService;

    @ResponseBody
    @PostMapping("/register")
    public ResponseEntity<ApiResponse<String>> register(@RequestBody UserWrapper userWrapper, HttpServletRequest request) {
        User registeredUser= userService.registerUser(userWrapper);
        return ResponseEntity.ok(new ApiResponse<>("success", "User registered. OTP sent to: " + registeredUser.getMobileNumber(), null, request.getRequestURI()));
    }

    @ResponseBody
    @PostMapping("/verify-otp")
    public ResponseEntity<ApiResponse<UserWrapper>> verifyOtp(@RequestParam @Email String email, @RequestParam String otp,HttpServletRequest request) {
        UserWrapper userWrapper = userService.verifyOtp(email, otp);
        return ResponseEntity.ok(new ApiResponse<>("success", "OTP verified successfully", userWrapper, request.getRequestURI()));

    }

    @ResponseBody
    @PostMapping("/login")
    public ResponseEntity<ApiResponse<UserWrapper>>login(@RequestParam String email,@RequestParam String password,HttpServletRequest request ){
        return ResponseEntity.ok(new ApiResponse<>("success", "Login successful", userService.login(email, password), "/api/user/login"));
    }

    @ResponseBody
    @PostMapping("/resend-otp")
    public ResponseEntity<ApiResponse<UserWrapper>>SendOtp(@RequestParam @Email String email,HttpServletRequest request) {
        return ResponseEntity.ok(new ApiResponse<>("success", "OTP resent successfully", userService.sendOtp(email), "/api/user/resend-otp"));
    }

    @ResponseBody
    @PostMapping("/forgot-password-send-otp")
    public ResponseEntity<ApiResponse<UserWrapper>> forgotPassword(@RequestParam @Email String email,HttpServletRequest request) {
        return ResponseEntity.ok(new ApiResponse<>("success", "OTP sent for password reset", userService.sendOtpForgotPassword(email), "/api/user/forgot-password-send-otp"));
    }

    @ResponseBody
    @PostMapping("/forgot-password-verify-otp")
    public ResponseEntity<ApiResponse<UserWrapper>> forgotPasswordVerifyOtp(@RequestParam @Email String email, @RequestParam String otp, @RequestParam String newPassword,HttpServletRequest request) {
        UserWrapper userWrapper = userService.updatePassword(email, otp,newPassword);

        return ResponseEntity.ok(new ApiResponse<>("success", "OTP verified successfully", userWrapper, request.getRequestURI()));
    }

    @ResponseBody
    @PostMapping("/forgot-password")
    public ResponseEntity<ApiResponse<String>> forgotPasswordSendLink(@RequestParam @Email String email,HttpServletRequest request) {
        userService.sendForgotPasswordLink(email);
        return ResponseEntity.ok(new ApiResponse<>("success","Password reset link sent to email.", null, request.getRequestURI()));

    }

    @ResponseBody
    @PostMapping("/reset-password")
    public ResponseEntity<ApiResponse<String>>resetPassword(@RequestParam String token,@RequestParam String password,@RequestParam String confirmPassword,HttpServletRequest request){
        System.out.println(token);
        userService.resetPassword(token, password, confirmPassword);
        return ResponseEntity.ok(new ApiResponse<>("success","Password reset successfully.", null, request.getRequestURI()));
    }
    @GetMapping("/reset-password")
    public String resetPasswordPage(@RequestParam String token, Model model) {
        System.out.println(token);
        model.addAttribute("token", token); // Pass the token to the template
        return "reset-password"; // Refers to reset-password.html in templates
    }
}