package com.qalaa.util;

import lombok.Getter;
import lombok.Setter;

import java.sql.Time;
import java.time.LocalDateTime;

@Getter@Setter
public class ApiResponse <T>{
    private String status;
    private String message;
    private T data;
    private LocalDateTime timestamp;
    private String path;

    public ApiResponse(String status, String message, T data, String path) {
        this.status = status;
        this.message = message;
        this.data = data;
        this.timestamp = LocalDateTime.now();
        this.path = path;
    }
}
