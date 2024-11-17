package com.qalaa.user.service;


import com.qalaa.user.model.User;
import com.qalaa.user.wrapper.UserWrapper;
import jakarta.validation.constraints.Email;

public interface UserService {


    User registerUser(UserWrapper userWrapper);

    UserWrapper verifyOtp(@Email String email, String otp);
}
