package com.qalaa.user.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.util.Date;

@Entity
@Table(name="users")
@Data
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String name;

    @Email
    @NotBlank
    private String email;

    @NotBlank
    @Column(unique = true)
    private String mobileNumber;

    @NotBlank
    private String password;

    private String otp;

    private Date expirationTimeOfOtp;

    private String forgotPasswordOtp;

    private Date expirationTimeOfForgotPasswordOtp;

    private boolean isVerified;

}
