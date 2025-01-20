package com.qalaa.exception;

import com.qalaa.exception.wrapper.ErrorResponseWrapper;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        Map<String, String> validationErrors = new HashMap<>();
        List<ObjectError> validationErrorList = ex.getBindingResult().getAllErrors();

        validationErrorList.forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String validationMsg = error.getDefaultMessage();
            validationErrors.put(fieldName, validationMsg);
        });
        return new ResponseEntity<>(validationErrors, HttpStatus.BAD_REQUEST);
    }


    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorResponseWrapper> handelResourceNotFoundException(ResourceNotFoundException exception, WebRequest webRequest) {

        ErrorResponseWrapper errorResponseWrapper = new ErrorResponseWrapper();
        errorResponseWrapper.setMessage(exception.getMessage());
        errorResponseWrapper.setStatus(HttpStatus.NOT_FOUND.value());
        errorResponseWrapper.setTimestamp(System.currentTimeMillis());
        errorResponseWrapper.setApiPath(webRequest.getDescription(false));
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponseWrapper);
    }

    @ExceptionHandler(CustomException.class)
    public ResponseEntity<ErrorResponseWrapper> handelCustomException(CustomException exception, WebRequest webRequest) {

        ErrorResponseWrapper errorResponseWrapper = new ErrorResponseWrapper();
        errorResponseWrapper.setMessage(exception.getMessage());
        errorResponseWrapper.setStatus(HttpStatus.BAD_REQUEST.value());
        errorResponseWrapper.setTimestamp(System.currentTimeMillis());
        errorResponseWrapper.setApiPath(webRequest.getDescription(false));
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponseWrapper);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponseWrapper> handelException(Exception exception, WebRequest webRequest) {

        ErrorResponseWrapper errorResponseWrapper = new ErrorResponseWrapper();
        errorResponseWrapper.setMessage(exception.getMessage());
        errorResponseWrapper.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
        errorResponseWrapper.setTimestamp(System.currentTimeMillis());
        errorResponseWrapper.setApiPath(webRequest.getDescription(false));
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponseWrapper);
    }


}
