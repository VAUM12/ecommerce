package com.qalaa.exception.wrapper;

import lombok.Data;

@Data
public class ErrorResponseWrapper {

    private String message;
    private int status;
    private long timestamp;
    private String apiPath;
}
