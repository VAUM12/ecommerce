package com.qalaa.user.wrapper;

import jakarta.validation.constraints.Email;
import lombok.Data;

@Data
public class UserWrapper {
    private String name;

    @Email
    private String email;
    private String mobileNumber;
    private String password;
    private String token;
}
