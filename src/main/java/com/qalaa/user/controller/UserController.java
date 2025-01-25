package com.qalaa.user.controller;

import com.qalaa.user.model.User;
import com.qalaa.user.service.UserService;
import com.qalaa.user.wrapper.UserWrapper;
import com.qalaa.util.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/user")
@SecurityRequirement(name = "bearerAuth")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping()
    public ResponseEntity<ApiResponse<UserWrapper>> getUser(@RequestAttribute("id") Long userId, HttpServletRequest request) {

        return ResponseEntity.ok(new ApiResponse<>("success", "User details", userService.getUserWrapper(userId), request.getRequestURI()));
    }
}
